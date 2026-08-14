package com.jurisai.dao.repository;

import com.jurisai.entity.Citations;
import com.jurisai.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CitationsRepo extends JpaRepository<Citations, Long> {
    @Query("SELECT c FROM Citations c WHERE c.id.accountId = :accountId AND c.id.messageId = :messageId")
    List<Citations> findByAccountAndMessage(@Param("accountId") Long accountId,
                                            @Param("messageId") Long messageId);
}
