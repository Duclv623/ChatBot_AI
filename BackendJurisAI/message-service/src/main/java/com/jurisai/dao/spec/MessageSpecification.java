package com.jurisai.dao.spec;

import com.jurisai.entity.Messages;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class MessageSpecification {
    public static Specification<Messages> fromCreatedBy(Long accountId, Long conversationId) {
        return (root, query, cb) -> {
            Predicate accountPredicate = cb.equal(root.get("id").get("accountId"), accountId);
            Predicate conversationPredicate = cb.equal(root.get("id").get("conversationsId"), conversationId);
            return cb.and(accountPredicate, conversationPredicate);
        };
    }

    private MessageSpecification() { }
}
