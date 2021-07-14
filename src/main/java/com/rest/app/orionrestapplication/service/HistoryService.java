package com.rest.app.orionrestapplication.service;

import com.rest.app.orionrestapplication.model.History;

import java.util.List;

public interface HistoryService {

    History addHistory(History history);

    void delete(Long id);

    List<History> getAll();
}
