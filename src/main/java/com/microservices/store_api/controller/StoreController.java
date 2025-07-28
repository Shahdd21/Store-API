package com.microservices.store_api.controller;

import com.microservices.store_api.dto.*;
import com.microservices.store_api.service.StoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/add")
    public void addStock(@RequestBody StockDetails stockDetails){
        storeService.addStock(stockDetails);
    }

    @PostMapping("/consume")
    public void consumeStock(@RequestBody StockDetails stockDetails){
        storeService.consumeStock(stockDetails);
    }

    @PostMapping("/warehouses")
    public List<StoreDTOByWarehouse> getStoreEntriesByWarehouse(@RequestParam(name = "warehouse") Long warehouseId){

        return storeService.getEntriesByWarehouseId(warehouseId);
    }

    @PostMapping("/products")
    public List<StoreDTOByProduct> getStoreEntriesByProduct(@RequestParam(name = "product") Long productId){

        return storeService.getEntriesByProductId(productId);
    }

    @DeleteMapping
    public void deleteStoreEntryByStoreId(Long productId, Long warehouseId){
        storeService.deleteStoreEntryByStoreId(productId, warehouseId);
    }


    @GetMapping("/products")
    public List<ProductDTO> getAllProducts(){
        return storeService.getAllProducts();
    }

    @GetMapping("/products/count")
    public Integer getNumberOfProducts(){
        return storeService.getNumberOfProducts();
    }

    @GetMapping("/count")
    public Integer getTotalStockOfStores(){
        return storeService.getTotalStockOfStores();
    }

    @GetMapping
    public List<StoreDTO> getStoreEntries(){
        return storeService.getStoreEntries();
    }
}
