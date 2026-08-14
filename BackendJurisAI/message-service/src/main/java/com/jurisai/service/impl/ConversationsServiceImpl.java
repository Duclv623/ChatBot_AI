package com.jurisai.service.impl;

import com.jurisai.dao.repository.ConversationsRepo;
import com.jurisai.dao.repository.MessagesRepo;
import com.jurisai.dao.spec.ConversationSpecification;
import com.jurisai.dao.spec.MessageSpecification;
import com.jurisai.entity.Conversations;
import com.jurisai.entity.Messages;
import com.jurisai.entity.pk.ConversationPK;
import com.jurisai.entity.pk.MessagePK;
import com.jurisai.service.ConversationsService;
import com.jurisai.service.MessagesService;
import com.jurisai.util.ConversationConstant;
import com.jurisai.util.PageableUtils;
import com.jurisai.util.SnowflakeIdGenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationsServiceImpl implements ConversationsService {
    private final MessagesService messagesService;
    private final ConversationsRepo conversationsRepo;
    private final SnowflakeIdGenUtils snowflakeIdGenUtils;
    @Override
    public void addOrUpdate(Conversations conversation) {
        conversationsRepo.save(conversation);
    }
    @Override
    public Conversations findByPimaryKey(Long accountId, Long conversationId) {
        return conversationsRepo.findByPimaryKey(accountId, conversationId);
    }

    @Override
    public Page<Conversations> findListConv(Integer size, Integer page, String sortBy, Long accountId) {
        Pageable pageable = PageableUtils.createPageable(size, page, sortBy);
        Specification<Conversations> specification = ConversationSpecification.fromCreatedBy(accountId);
        return conversationsRepo.findAll(specification, pageable);
    }
    @Override
    public boolean deleteConversation(Long accountId, Long conversationId) {
        Conversations conversation = findByPimaryKey(accountId, conversationId);
        if (conversation == null) {
            return false;
        }

        // delete List message
        messagesService.deleteListMessages(accountId, conversationId);

        // delete conversation
        conversationsRepo.delete(conversation);
        return true;
    }

    @Override
    public ConversationPK genConversationPK(Long accountId) {
        long conversationId = snowflakeIdGenUtils.nextId();;
        return new ConversationPK(accountId, conversationId);
    }

    @Override
    public boolean checkParentConv(Long accountId, Long parentConvId) {
        if(parentConvId == ConversationConstant.ParrentConvStatus.NEW_CONV.getValue()){
            return true;
        }
        Conversations conversation = findByPimaryKey(accountId, parentConvId);
        if (conversation == null){
            return false;
        }
        return conversation.getStatus() != ConversationConstant.ConversationStatus.OPEN.getValue();
    }



}
