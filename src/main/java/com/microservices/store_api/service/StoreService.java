package com.microservices.store_api.service;

import com.microservices.store_api.dto.*;
import com.microservices.store_api.rabbit.StockResponse;

import java.util.List;
import java.util.Map;

public interface StoreService {

    void addStock(StockDetails stockDetails);

    void consumeStock(StockDetails stockDetails);

    List<StoreDTOByWarehouse> getEntriesByWarehouseId(Long warehouseId);

    List<StoreDTOByProduct> getEntriesByProductId(Long productId);

    List<ProductDTO> getAllProducts();

    Integer getNumberOfProducts();

    Integer getTotalStockOfStores();

    void deleteStoreEntryByStoreId(Long productId, Long warehouseId);

    List<StoreDTO> getStoreEntries();

    StockResponse consumeStockForOrder(Long orderId, Map<Long, Integer> products_quantities);
}
