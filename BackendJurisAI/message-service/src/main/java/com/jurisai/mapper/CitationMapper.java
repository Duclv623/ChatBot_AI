package com.jurisai.mapper;

import com.jurisai.dto.response.*;
import com.jurisai.entity.Citations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CitationMapper {

    public static CitationResponseDTO toDto(Citations entity) {
        return CitationResponseDTO.builder()
                .lawName(entity.getLawName())
                .chapter(entity.getChapter())
                .section(entity.getSection())
                .article(entity.getArticle())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static ListCitationResponseDTO toListResponse(List<Citations> citationsList) {
        List<CitationResponseDTO> citations = citationsList.stream()
                .map(CitationMapper::toDto).toList();

        // Lấy info first Citation
        Citations firstCitations = citationsList.get(0);

        return ListCitationResponseDTO.builder()
                .messageId(firstCitations.getId().getMessageId())
                .citations(citations)
                .build();
    }
}
