package com.jurisai.dao.repository;

import com.jurisai.entity.Conversations;
import com.jurisai.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationsRepo extends JpaRepository<Conversations, Long>, JpaSpecificationExecutor<Conversations> {
    @Query("SELECT c FROM Conversations c WHERE c.id.accountId = :accountId AND c.id.conversationsId = :conversationId ")
    Conversations findByPimaryKey(@Param("accountId") Long accountId,
                                  @Param("conversationId") Long conversationId);
}