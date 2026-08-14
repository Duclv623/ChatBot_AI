package com.jurisai.service.impl;


import com.jurisai.dao.spec.MessageSpecification;
import com.jurisai.entity.Citations;
import com.jurisai.entity.Messages;
import com.jurisai.entity.pk.CitationPK;
import com.jurisai.entity.pk.MessagePK;
import com.jurisai.dao.repository.MessagesRepo;
import com.jurisai.service.CitationsService;
import com.jurisai.service.MessagesService;
import com.jurisai.util.PageableUtils;
import com.jurisai.util.SnowflakeIdGenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessagesServiceImpl implements MessagesService {

    private final MessagesRepo messagesRepo;
    private final SnowflakeIdGenUtils snowflakeIdGenUtils;
    private final CitationsService citationsService;

    @Override
    public void addOrUpdate( Messages messages) {
        long timestamp = new Date().getTime();

        // check message have parent id
        // long parentMessagesId = checkParentMessage(accountId, conversationId, messageId);
        long parentMessagesId = 1L;

        messages.setCreatedAt(timestamp);
        messages.setUpdatedAt(timestamp);

        messagesRepo.save(messages);
    }
    @Override
    public Messages findByPimaryKey(Long accountId, Long conversationId, Long messageId) {
        return messagesRepo.findByPimaryKey(accountId, conversationId, messageId);
    }

    @Override
    public Page<Messages> findListMessage(Integer size, Integer page, String sortBy, Long accountId, Long conversationId) {
        Pageable pageable = PageableUtils.createPageable(size, page, sortBy);
        Specification<Messages> specification = MessageSpecification.fromCreatedBy(accountId, conversationId);
        return messagesRepo.findAll(specification, pageable);
    }



    @Override
    public MessagePK genMessagePK(Long accountId, Long conversationId) {
        // gen id
        long messageId = snowflakeIdGenUtils.nextId();
        return new MessagePK(accountId, conversationId, messageId);
    }

    @Override
    public List<Messages> getListMessages(Long accountId, Long conversationId) {
        Specification<Messages> specification = MessageSpecification.fromCreatedBy(accountId, conversationId);
        return messagesRepo.findAll(specification);
    }

    @Override
    public void deleteListMessages(Long accountId, Long conversationId) {
        Specification<Messages> specification = MessageSpecification.fromCreatedBy(accountId, conversationId);
        messagesRepo.delete(specification);
    }

    @Override
    public void processAiResponseMessage(String data) {
        long timeStamp = new Date().getTime();
        JSONObject jsonObject = new JSONObject(data);
        long messageId = jsonObject.getLong("message_id");
        long accountId = jsonObject.getLong("account_id");
        long conversationId = jsonObject.getLong("conversations_id");
        String modelChat = jsonObject.getString("model_chat");
        String answer = jsonObject.getString("content");
        JSONArray citations = jsonObject.getJSONArray("citations");

        for(int i = 0; i < citations.length(); i++) {
            JSONObject citationsJSONObject = citations.getJSONObject(i);
            String lawName = citationsJSONObject.getString("law_name");
            String chapter = citationsJSONObject.getString("chapter");
            String section = citationsJSONObject.getString("section");
            String article = citationsJSONObject.getString("article");
            CitationPK citationPK = citationsService.genCitationPK(accountId, messageId);
            Citations citation = Citations.builder()
                    .id(citationPK)
                    .lawName(lawName)
                    .chapter(chapter)
                    .section(section)
                    .article(article)
                    .createdAt(timeStamp)
                    .updatedAt(timeStamp)
                    .build();
            citationsService.addOrUpdate(citation);
        }

        MessagePK messagePK = new MessagePK(accountId,conversationId, messageId);
        Messages messages = Messages.builder()
                .id(messagePK)
                .status(2)
                .sender(0)
                .content(answer)
                .modelChat(modelChat)
                .updatedAt(timeStamp)
                .build();

        addOrUpdate(messages);
    }
}
