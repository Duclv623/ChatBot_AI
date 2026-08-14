package com.jurisai.service.impl;

import com.jurisai.dao.repository.CitationsRepo;
import com.jurisai.entity.Citations;
import com.jurisai.entity.pk.CitationPK;
import com.jurisai.entity.pk.MessagePK;
import com.jurisai.service.CitationsService;
import com.jurisai.util.SnowflakeIdGenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CitationsServiceImpl implements CitationsService {

    private final CitationsRepo citationsRepo;

    private final SnowflakeIdGenUtils snowflakeIdGenUtils;

    @Override
    public List<Citations> getList(Long accountId, Long messageId) {
        return citationsRepo.findByAccountAndMessage(accountId, messageId);
    }

    @Override
    public CitationPK genCitationPK(long accountId, long messageId) {
        long citationId = snowflakeIdGenUtils.nextId();
        return new CitationPK(accountId, messageId, citationId);
    }

    @Override
    public void addOrUpdate(Citations citation) {
        citationsRepo.save(citation);
    }
}
