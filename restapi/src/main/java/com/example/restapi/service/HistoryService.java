package com.example.restapi.service;

import com.example.restapi.model.entity.History;

public interface HistoryService {

    History save(History history);

    History findByAmbulance(long ambulanceId, String code);

    History findByAmbulanceAndTime(long ambulanceId, String code);

    History findById(long id);

}