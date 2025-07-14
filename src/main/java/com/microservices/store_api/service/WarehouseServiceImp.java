package com.microservices.store_api.service;

import com.microservices.store_api.entity.Warehouse;
import com.microservices.store_api.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WarehouseServiceImp implements WarehouseService{

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImp(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }


    @Override
    public void addWarehouseLocation(String location) {
        Warehouse warehouse = new Warehouse(location);

        warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse findWarehouseById(Long warehouseId) {

        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);

        if(warehouse.isEmpty()) throw new NoSuchElementException("no warehouse with id - " + warehouse);

        return warehouse.get();
    }

    @Override
    public List<Warehouse> getAllLocations() {
        return warehouseRepository.findAll();
    }

    @Override
    public Integer getNumberOfWarehouses() {
        return warehouseRepository.findAll().size();
    }

    @Override
    public void deleteWarehouseById(Long warehouseId) {

        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        try {
            if(warehouse.isEmpty()) throw new NoSuchElementException("no warehouse with id - " + warehouse);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }

        warehouseRepository.deleteById(warehouseId);
    }
}
