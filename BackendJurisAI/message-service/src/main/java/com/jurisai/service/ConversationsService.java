package com.jurisai.service;

import com.jurisai.entity.Conversations;
import com.jurisai.entity.pk.ConversationPK;
import org.springframework.data.domain.Page;

public interface ConversationsService {
    boolean deleteConversation(Long accountId, Long conversationId);

    ConversationPK genConversationPK(Long accountId);

    boolean checkParentConv(Long accountId, Long parentConvId);

    void addOrUpdate(Conversations conversation);

    Page<Conversations> findListConv(Integer size, Integer page, String sortBy, Long accountId);

    Conversations findByPimaryKey(Long accountId, Long conversationId);
}
