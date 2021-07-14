package com.rest.app.orionrestapplication.service.impl;

import com.rest.app.orionrestapplication.model.History;
import com.rest.app.orionrestapplication.repository.HistoryRepository;
import com.rest.app.orionrestapplication.service.HistoryService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public History addHistory(History history) {
        var historyResponse = historyRepository.save(history);
        log.info("History added. Response: " + historyResponse.toString());
        return historyResponse;
    }

    @Override
    public void delete(Long id) {
        log.info("Delete history with id=" + id);
        historyRepository.deleteById(id);
    }

    @Override
    public List<History> getAll() {
        List<History> histories = historyRepository.findAll();
        log.info("Get all history. Count rows=" + histories.size());
        return histories;
    }
}
