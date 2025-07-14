package com.microservices.store_api.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    private String location;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.REMOVE)
    private List<Store> storeEntries; // all products in branch x

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.REMOVE)
    private List<History> operationsHistory;

    public Warehouse(){}

    public Warehouse(String location) {
        this.location = location;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
