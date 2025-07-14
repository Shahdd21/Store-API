package com.microservices.store_api.service;

import com.microservices.store_api.dto.HistoryDTO;
import com.microservices.store_api.dto.StockDetails;
import com.microservices.store_api.entity.History;
import com.microservices.store_api.entity.Warehouse;
import com.microservices.store_api.enums.Operation;
import com.microservices.store_api.repository.HistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class HistoryServiceImp implements HistoryService{

    private final HistoryRepository historyRepository;
    private final WarehouseService warehouseService;

    public HistoryServiceImp(HistoryRepository historyRepository, WarehouseService warehouseService) {
        this.historyRepository = historyRepository;
        this.warehouseService = warehouseService;
    }

    @Override
    public void saveOperation(StockDetails stockDetails, Operation operation) {

        Warehouse warehouse = warehouseService.findWarehouseById(stockDetails.getWarehouseId());

        History historyRecord = new History(stockDetails.getProductId(), warehouse,
                operation, stockDetails.getQuantity(), LocalDate.now());

        historyRepository.save(historyRecord);
    }

    @Override
    public List<HistoryDTO> getAllRecords() {
        List<History> historyList = historyRepository.findAll();
        List<HistoryDTO> historyDTOS = new ArrayList<>();

        for(History history : historyList){
            historyDTOS.add(new HistoryDTO(history));
        }

        return historyDTOS;
    }

    @Override
    public List<HistoryDTO> getRecordsByWarehouseId(Long warehouseId) {

        List<History> historyList = historyRepository.findByWarehouse_WarehouseId(warehouseId);
        List<HistoryDTO> historyDTOS = new ArrayList<>();

            if (historyList == null) throw new NoSuchElementException("no records with warehouse id - " + warehouseId);

            else {
                for (History history : historyList) {
                    historyDTOS.add(new HistoryDTO(history));
                }
            }

        return historyDTOS;
    }

    @Override
    public List<HistoryDTO> getRecordsByProductId(Long productId) {

        List<History> historyList = historyRepository.findByProductId(productId);
        List<HistoryDTO> historyDTOS = new ArrayList<>();

            if (historyList == null) throw new NoSuchElementException("no records with product id - " + productId);

            else {
                for (History history : historyList) {
                    historyDTOS.add(new HistoryDTO(history));
                }
            }

        return historyDTOS;
    }

    @Override
    public List<HistoryDTO> getRecordsMatchingBothKeys(Long warehouseId, Long productId) {

        List<History> historyList = historyRepository.findByWarehouse_WarehouseIdAndProductId(warehouseId,productId);
        List<HistoryDTO> historyDTOS = new ArrayList<>();

            if (historyList == null) {
                throw new NoSuchElementException("no records with warehouse id - " + warehouseId
                        + "product id - " + productId);
            }

            else {
                for (History history : historyList) {
                    historyDTOS.add(new HistoryDTO(history));
                }
            }

        return historyDTOS;
    }

    @Override
    public void deleteRecordsByBothKeys(Long warehouseId, Long productId) {

        List<History> history = historyRepository.findByWarehouse_WarehouseIdAndProductId(warehouseId, productId);

        if (history == null) {
            throw new NoSuchElementException("no records with warehouse id - " + warehouseId
                    + "product id - " + productId);
        }

        historyRepository.deleteByWarehouse_WarehouseIdAndProductId(warehouseId, productId);
    }

    @Override
    public void deleteRecordsByWarehouseId(Long warehouseId) {

        List<History> history = historyRepository.findByWarehouse_WarehouseId(warehouseId);

        if (history == null) throw new NoSuchElementException("no records with warehouse id - " + warehouseId);

        historyRepository.deleteByWarehouse_WarehouseId(warehouseId);
    }

    @Override
    public void deleteRecordsByProductId(Long productId) {

        List<History> history = historyRepository.findByProductId(productId);

        if (history == null) throw new NoSuchElementException("no records with product id - " + productId);

        historyRepository.deleteByProductId(productId);
    }
}
