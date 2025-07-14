package com.microservices.store_api.repository;

import com.microservices.store_api.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByWarehouse_WarehouseId(Long warehouseId);

    List<History> findByProductId(Long productId);

    List<History> findByWarehouse_WarehouseIdAndProductId(Long warehouseId, Long productId);

    void deleteByWarehouse_WarehouseId(Long warehouseId);

    void deleteByProductId(Long productId);

    void deleteByWarehouse_WarehouseIdAndProductId(Long warehouseId, Long productId);
}
