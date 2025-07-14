package com.microservices.store_api.service;

import com.microservices.store_api.dto.HistoryDTO;
import com.microservices.store_api.dto.StockDetails;
import com.microservices.store_api.entity.History;
import com.microservices.store_api.enums.Operation;

import java.util.List;

public interface HistoryService {

    void saveOperation(StockDetails stockDetails, Operation operation);

    List<HistoryDTO> getAllRecords();

    List<HistoryDTO> getRecordsByWarehouseId(Long warehouseId);

    List<HistoryDTO> getRecordsByProductId(Long productId);

    List<HistoryDTO> getRecordsMatchingBothKeys(Long warehouseId, Long productId);

    void deleteRecordsByBothKeys(Long warehouseId, Long productId);

    void deleteRecordsByWarehouseId(Long warehouseId);

    void deleteRecordsByProductId(Long productId);
}
