package com.microservices.store_api.repository;

import com.microservices.store_api.entity.Store;
import com.microservices.store_api.keys.StoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, StoreId> {

    List<Store> findByWarehouse_WarehouseId(Long warehouseId);

    List<Store> findByStoreId_ProductId(Long productId);
}
