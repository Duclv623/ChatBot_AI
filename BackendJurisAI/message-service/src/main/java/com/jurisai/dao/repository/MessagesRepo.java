package com.jurisai.dao.repository;

import com.jurisai.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepo extends JpaRepository<Messages, Long>, JpaSpecificationExecutor<Messages> {
    @Query("SELECT m FROM Messages m WHERE m.id.accountId = :accountId AND m.id.conversationsId = :conversationId AND m.id.messageId = :messageId")
    Messages findByPimaryKey(@Param("accountId") Long accountId,
                             @Param("conversationId") Long conversationId,
                             @Param("messageId") Long messageId);
}
