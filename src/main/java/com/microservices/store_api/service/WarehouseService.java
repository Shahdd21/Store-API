package com.microservices.store_api.service;

import com.microservices.store_api.dto.StockDetails;
import com.microservices.store_api.entity.Warehouse;

import java.util.List;

public interface WarehouseService {

    void addWarehouseLocation(String location);

    Warehouse findWarehouseById(Long warehouseId);

    List<Warehouse> getAllLocations();

    Integer getNumberOfWarehouses();

    void deleteWarehouseById(Long warehouseId);
}
