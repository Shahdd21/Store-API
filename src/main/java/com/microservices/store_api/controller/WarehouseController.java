package com.microservices.store_api.controller;

import com.microservices.store_api.entity.Warehouse;
import com.microservices.store_api.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/add")
    public void addWarehouseLocation(@RequestBody String location) {

        warehouseService.addWarehouseLocation(location);
    }

    @GetMapping
    public List<Warehouse> getAllLocations() {
        return warehouseService.getAllLocations();
    }

    @GetMapping("/count")
    public Integer getNumberOfWarehouses() {
        return warehouseService.getNumberOfWarehouses();
    }

    @DeleteMapping
    public void deleteWarehouseById(@RequestParam(name = "warehouse") Long warehouseId){
        warehouseService.deleteWarehouseById(warehouseId);
    }
}