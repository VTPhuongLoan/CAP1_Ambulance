package com.example.restapi.service.impl;

import com.example.restapi.model.entity.History;
import com.example.restapi.repository.HistoryRepository;
import com.example.restapi.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public History save(History history) {
        return historyRepository.save(history);
    }

    @Override
    public History findByAmbulance(long ambulanceId, String code) {
        return historyRepository.findByAmbulance(ambulanceId, code).orElse(null);
    }

    @Override
    public History findByAmbulanceAndTime(long ambulanceId, String code) {
        return historyRepository.findByAmbulanceAndTime(ambulanceId, code).orElse(null);
    }

    @Override
    public History findById(long id) {
        return historyRepository.findById(id).orElse(null);
    }

}
