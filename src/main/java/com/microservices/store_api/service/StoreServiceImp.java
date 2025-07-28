package com.microservices.store_api.service;

import com.microservices.store_api.dto.*;
import com.microservices.store_api.entity.Store;
import com.microservices.store_api.enums.Operation;
import com.microservices.store_api.feign.ProductServiceClient;
import com.microservices.store_api.keys.StoreId;
import com.microservices.store_api.rabbit.ProductAvailabilityService;
import com.microservices.store_api.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class StoreServiceImp implements StoreService{

    private final StoreRepository storeRepository;
    private final HistoryService historyService;
    private final WarehouseService warehouseService;
    private final ProductServiceClient productServiceClient;
    private final ProductAvailabilityService productAvailabilityService;

    public StoreServiceImp(StoreRepository storeRepository, HistoryService historyService, WarehouseService warehouseService, ProductServiceClient productServiceClient, ProductAvailabilityService productAvailabilityService) {
        this.storeRepository = storeRepository;
        this.historyService = historyService;
        this.warehouseService = warehouseService;
        this.productServiceClient = productServiceClient;
        this.productAvailabilityService = productAvailabilityService;
    }

    @Override
    public void addStock(StockDetails stockDetails) {

        StoreId id = new StoreId(stockDetails.getProductId(), stockDetails.getWarehouseId());

        // check if the exact product exists in the same store
        Optional<Store> existingStore = storeRepository.findById(id);

        Store store;
        if(existingStore.isPresent()){

            store = existingStore.get();

            Integer initialQuantity = store.getQuantity();

            store.setQuantity(initialQuantity + stockDetails.getQuantity());
            store.setUpdatedAt(LocalDate.now());

        }

        else {
            store = new Store();
            store.setWarehouse(warehouseService.findWarehouseById(stockDetails.getWarehouseId()));
            store.setStoreId(id);
            store.setQuantity(stockDetails.getQuantity());
            store.setUpdatedAt(LocalDate.now());
        }

        storeRepository.save(store);

        checkAvailability(stockDetails.getProductId());

        // add the transaction to history
        historyService.saveOperation(stockDetails, Operation.ADD);

    }

    @Override
    public void consumeStockForOrder(Map<Long, Integer> products_quantities){

        for(Map.Entry<Long, Integer> entry : products_quantities.entrySet()) {
            List<Store> productsFound = storeRepository.findByStoreId_ProductId(entry.getKey());

            Integer quantityToBeConsumed = entry.getValue();
            Integer initialQuantity = entry.getValue();

                for (Store store : productsFound) {

                    if(quantityToBeConsumed <= 0) break;

                    if(store.getQuantity() >= quantityToBeConsumed ){

                        store.setQuantity(store.getQuantity()-quantityToBeConsumed);
                        quantityToBeConsumed = 0;
                    }

                    else{
                        quantityToBeConsumed -= store.getQuantity();
                        store.setQuantity(0);
                    }

                    //save the modified entry
                    storeRepository.save(store);

                    //notify the product service if the product is totally sold out
                    checkAvailability(entry.getKey());

                    //log history
                    historyService.saveOperation(new StockDetails(store.getStoreId().getProductId(), store.getStoreId().getWarehouseId(),
                            initialQuantity), Operation.CONSUME);
                }

                if(quantityToBeConsumed > 0 ) System.out.println("Need additional quantities of product with id - " + entry.getKey()
                + " quantity required: "+ quantityToBeConsumed);
        }
    }

    @Override
    public void consumeStock(StockDetails stockDetails) {

        StoreId id = new StoreId(stockDetails.getProductId(), stockDetails.getWarehouseId());

        // check if the product exists in the same store
        Optional<Store> existingStore = storeRepository.findById(id);

            if (existingStore.isPresent()) {

                Integer initialQuantity = existingStore.get().getQuantity();

                    if (initialQuantity < stockDetails.getQuantity())
                        throw new UnsupportedOperationException("The given quantity is more than what's found");

                existingStore.get().setQuantity(initialQuantity - stockDetails.getQuantity());
                existingStore.get().setUpdatedAt(LocalDate.now());

                checkAvailability(stockDetails.getProductId());

            } else {

                throw new NoSuchElementException("The product or warehouse doesn't exist");
            }

        historyService.saveOperation(stockDetails, Operation.CONSUME);
    }

    private void checkAvailability(Long productId) {

        boolean available = storeRepository.findByStoreId_ProductId(productId)
                .stream()
                .anyMatch(store -> store.getQuantity() > 0);

        if(!available){
            productAvailabilityService.sendMessage(productId, false);
        }
        else {
            productAvailabilityService.sendMessage(productId, true);
        }
    }

    @Override
    public List<StoreDTOByWarehouse> getEntriesByWarehouseId(Long warehouseId) {
        List<Store> storeEntries = storeRepository.findByWarehouse_WarehouseId(warehouseId);
        List<StoreDTOByWarehouse> storeDTOS = new ArrayList<>();

            if (storeEntries == null) throw new NoSuchElementException("no warehouse with id - " + warehouseId);

            else {
                for (Store store : storeEntries) {

                    ProductDTO productDTO = productServiceClient.getProductDTOForStore(store.getStoreId().getProductId());
                    StoreDTOByWarehouse storeDTO = new StoreDTOByWarehouse(productDTO, store);

                    storeDTOS.add(storeDTO);
                }
            }
        return storeDTOS;
    }

    @Override
    public List<StoreDTOByProduct> getEntriesByProductId(Long productId) {
        List<Store> storeEntries = storeRepository.findByStoreId_ProductId(productId);
        List<StoreDTOByProduct> storeDTOS = new ArrayList<>();

            if (storeEntries == null) throw new NoSuchElementException("no product with id - " + productId);

            else {
                for (Store store : storeEntries) {
                    String location = warehouseService
                            .findWarehouseById(store.getStoreId().getWarehouseId()).getLocation();
                    storeDTOS.add(new StoreDTOByProduct(store, location));
                }
            }

        return storeDTOS;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productServiceClient.getAllProductsForStore();
    }

    @Override
    public Integer getNumberOfProducts() {
        return getAllProducts().size();
    }

    @Override
    public Integer getTotalStockOfStores() {
        return storeRepository.findAll().stream()
                .mapToInt(Store::getQuantity)
                .sum();
    }

    @Override
    public void deleteStoreEntryByStoreId(Long productId, Long warehouseId) {

        StoreId storeId = new StoreId(productId, warehouseId);

        Optional<Store> storeEntry = storeRepository.findById(storeId);

        if (storeEntry.isEmpty())
            throw new NoSuchElementException("no entries with product id " + productId + " and warehouse id " + warehouseId);

        storeRepository.deleteById(storeId);
    }

    @Override
    public List<StoreDTO> getStoreEntries() {

        List<Store> storeEntries = storeRepository.findAll();
        List<StoreDTO> storeDTOS = new ArrayList<>();

        for(Store store: storeEntries){
            storeDTOS.add(new StoreDTO(store));
        }

        return storeDTOS;
    }
}
