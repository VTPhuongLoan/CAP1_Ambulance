package com.hrs.service;

import com.hrs.model.dto.AccountDTO;
import com.hrs.model.dto.MessageManagerDTO;
import com.hrs.model.reponse.MessageManagerResponse;

import java.util.List;

public interface MessageManagerService {

    List<MessageManagerResponse> getList(String token);

    MessageManagerResponse getById(String token, long id);

    String save(MessageManagerDTO messageManagerDTO, String token);

}
