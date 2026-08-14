package com.jurisai.mapper;

import com.jurisai.dto.request.ConversationRequestDTO;
import com.jurisai.dto.response.*;
import com.jurisai.entity.Conversations;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConversationMapper {
    public static ConversationResponseDTO toDto(Conversations entity) {
        return ConversationResponseDTO.builder()
                .conversationId(entity.getId().getConversationsId())
                .parentConvId(entity.getParentConvId())
                .title(entity.getTitle())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static Conversations toEntity(ConversationRequestDTO dto) {
        return Conversations.builder()
                .parentConvId(dto.getParentConvId())
                .title(dto.getTitle())
                .status(dto.getStatus())
                .build();
    }


    public static ListConversationResponseDTO toListResponse(Page<Conversations> conversationsPage) {
        List<ConversationResponseDTO> conversations = conversationsPage.getContent().stream()
                .map(ConversationMapper::toDto)
                .toList();

        // Lấy info chung từ phần tử đầu tiên
        Conversations firstConversation = conversationsPage.getContent().get(0);

        return ListConversationResponseDTO.builder()
                .accountId(firstConversation.getId().getAccountId())
                .conversations(conversations)
                .pagination(PaginationDTO.builder()
                        .totalPages(conversationsPage.getTotalPages())
                        .totalElements((int) conversationsPage.getTotalElements())
                        .currentPage(conversationsPage.getNumber())
                        .pageSize(conversationsPage.getSize())
                        .build())
                .build();
    }

    public static SingleConversationResponseDTO toSingleResponse(Conversations entity) {
        return SingleConversationResponseDTO.builder()
                .accountId(entity.getId().getAccountId())
                .conversation(toDto(entity))
                .build();
    }

}

