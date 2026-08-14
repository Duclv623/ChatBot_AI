package com.jurisai.service;

import com.jurisai.dto.request.MessageRequestDTO;
import com.jurisai.entity.Messages;
import com.jurisai.entity.pk.MessagePK;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface MessagesService {


    Messages findByPimaryKey(Long accountId, Long conversationId, Long messageId);

    Page<Messages> findListMessage(Integer size, Integer page, String sortBy, Long accountId, Long conversationId);

    void addOrUpdate(Messages messages);

    MessagePK genMessagePK(Long accountId, Long conversationId);

    List<Messages> getListMessages(Long accountId, Long conversationId);

    void deleteListMessages(Long accountId, Long conversationId);

    void processAiResponseMessage(String data);
}
