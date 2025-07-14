package com.microservices.store_api.controller;

import com.microservices.store_api.dto.HistoryDTO;

import com.microservices.store_api.service.HistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public List<HistoryDTO> getAllRecords(){
        return historyService.getAllRecords();
    }

    @PostMapping
    public List<HistoryDTO> getRecordsBy(@RequestParam(required = false, name = "warehouse") Long warehouseId,
                                    @RequestParam(required = false, name = "product") Long productId) {

            if (warehouseId != null && productId != null) {
                return historyService.getRecordsMatchingBothKeys(warehouseId, productId);
            } else if (warehouseId != null) {
                return historyService.getRecordsByWarehouseId(warehouseId);
            } else if (productId != null) {
                return historyService.getRecordsByProductId(productId);
            } else {
                throw new UnsupportedOperationException("must provide warehouse or product id");
            }
    }

    @DeleteMapping("/remove")
    public void deleteRecordsBy(@RequestParam(required = false, name = "warehouse") Long warehouseId,
                                @RequestParam(required = false, name = "product") Long productId){

            if (warehouseId != null && productId != null) {
                historyService.deleteRecordsByBothKeys(warehouseId, productId);
            } else if (warehouseId != null) {
                historyService.deleteRecordsByWarehouseId(warehouseId);
            } else if (productId != null) {
                historyService.deleteRecordsByProductId(productId);
            } else {
                throw new UnsupportedOperationException("must provide warehouse or product id");
            }
    }
}
