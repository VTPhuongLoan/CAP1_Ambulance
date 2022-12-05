package com.hrs.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hrs.model.dto.AccountDTO;
import com.hrs.model.dto.MessageManagerDTO;
import com.hrs.model.reponse.AmbulanceResponse;
import com.hrs.model.reponse.DTOResponse;
import com.hrs.model.reponse.MessageManagerResponse;
import com.hrs.service.AmbulanceService;
import com.hrs.service.MessageManagerService;
import com.hrs.utils.ConstantUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Service
public class MessageManagerServiceImpl implements MessageManagerService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<MessageManagerResponse> getList(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            String url = ConstantUtils.HOST_URL + "api/message/list";
            HttpEntity<String> entity = new HttpEntity<>(token, headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type typeData = new TypeToken<DTOResponse<List<MessageManagerResponse>>>() {
            }.getType();
            DTOResponse<List<MessageManagerResponse>> dtoResponse = gson.fromJson(respData, typeData);
            return dtoResponse.getData();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public MessageManagerResponse getById(String token, long id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            String url = ConstantUtils.HOST_URL + "api/message/id/" + id;
            HttpEntity<String> entity = new HttpEntity<>(token, headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type typeData = new TypeToken<DTOResponse<MessageManagerResponse>>() {
            }.getType();
            DTOResponse<MessageManagerResponse> dtoResponse = gson.fromJson(respData, typeData);
            return dtoResponse.getData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String save(MessageManagerDTO messageManagerDTO, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            HttpEntity<MessageManagerDTO> entity = new HttpEntity<>(messageManagerDTO, headers);
            String url = ConstantUtils.HOST_URL + "api/message/save";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return response.getBody();
        } catch (Exception ex) {
            return null;
        }
    }

}
