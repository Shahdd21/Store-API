package com.microservices.store_api.service;

import com.microservices.store_api.dto.*;
import com.microservices.store_api.entity.Store;
import com.microservices.store_api.enums.Operation;
import com.microservices.store_api.feign.ProductServiceClient;
import com.microservices.store_api.keys.StoreId;
import com.microservices.store_api.rabbit.MessageService;
import com.microservices.store_api.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class StoreServiceImp implements StoreService{

    private final StoreRepository storeRepository;
    private final HistoryService historyService;
    private final WarehouseService warehouseService;
    private final ProductServiceClient productServiceClient;
    private final MessageService messageService;

    public StoreServiceImp(StoreRepository storeRepository, HistoryService historyService, WarehouseService warehouseService, ProductServiceClient productServiceClient, MessageService messageService) {
        this.storeRepository = storeRepository;
        this.historyService = historyService;
        this.warehouseService = warehouseService;
        this.productServiceClient = productServiceClient;
        this.messageService = messageService;
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
        // add the transaction to history
        historyService.saveOperation(stockDetails, Operation.ADD);

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

                Store savedEntry = storeRepository.save(existingStore.get());

                if (savedEntry.getQuantity() == 0) {
                    messageService.sendMessage(savedEntry.getStoreId().getProductId(), false);
                }
            } else {

                throw new NoSuchElementException("The product or warehouse doesn't exist");
            }

        historyService.saveOperation(stockDetails, Operation.CONSUME);
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
