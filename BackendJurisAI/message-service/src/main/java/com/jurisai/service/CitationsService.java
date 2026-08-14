package com.jurisai.service;

import com.jurisai.entity.Citations;
import com.jurisai.entity.pk.CitationPK;

import java.util.List;

public interface CitationsService {
    List<Citations> getList(Long accountId, Long messageId);

    CitationPK genCitationPK(long accountId, long messageId);

    void addOrUpdate(Citations citation);
}
