package com.jurisai.mapper;

import com.jurisai.dto.request.MessageRequestDTO;
import com.jurisai.dto.response.ListMessagesResponseDTO;
import com.jurisai.dto.response.MessageResponseDTO;
import com.jurisai.dto.response.PaginationDTO;
import com.jurisai.dto.response.SingleMessageResponseDTO;
import com.jurisai.entity.Messages;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper {
    public static MessageResponseDTO toDto(Messages entity) {
        return MessageResponseDTO.builder()
                .messageId(entity.getId().getMessageId())
                .parentMessagesId(entity.getParentMessagesId())
                .content(entity.getContent())
                .indexMessages(entity.getIndexMessages())
                .sender(entity.getSender())
                .status(entity.getStatus())
                .fileUuid(entity.getFileUuid())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static Messages toEntity(MessageRequestDTO dto) {
        return Messages.builder()
                .parentMessagesId(dto.getParentMessagesId())
                .content(dto.getContent())
                .sender(dto.getSender())
                .status(dto.getStatus())
                .modelChat(dto.getModelChat())
                .fileUuid(dto.getFileUuid())
                .build();
    }

//    public static List<MessageResponseDTO> pageToList(Page<Messages> messages) {
//        return messages.getContent().stream()
//                    .map(message -> {
//                            MessageResponseDTO messageResponse = new MessageResponseDTO();
//                            messageResponse.setMessageId(message.getId().getMessageId());
//                            messageResponse.setAccountId(message.getId().getAccountId());
//                            messageResponse.setConversationsId(message.getId().getConversationsId());
//                            messageResponse.setParentMessagesId(message.getParentMessagesId());
//                            messageResponse.setContent(message.getContent());
//                            messageResponse.setIndexMessages(message.getIndexMessages());
//                            messageResponse.setSender(message.getSender());
//                            messageResponse.setStatus(message.getStatus());
//                            messageResponse.setFileUuid(message.getFileUuid());
//                            messageResponse.setModelChat(message.getModelChat());
//                            messageResponse.setCreatedAt(message.getCreatedAt());
//                            messageResponse.setUpdatedAt(message.getUpdatedAt());
//
//                            return messageResponse;
//                        })
//                    .toList();
//    }

    public static ListMessagesResponseDTO toListResponse(Page<Messages> messagesPage) {
        List<MessageResponseDTO> messages = messagesPage.getContent().stream()
                .map(MessageMapper::toDto)
                .toList();

        // Lấy info chung từ phần tử đầu tiên
        Messages firstMessage = messagesPage.getContent().get(0);

        return ListMessagesResponseDTO.builder()
                .accountId(firstMessage.getId().getAccountId())
                .conversationsId(firstMessage.getId().getConversationsId())
                .modelChat(firstMessage.getModelChat())
                .messages(messages)
                .pagination(PaginationDTO.builder()
                        .totalPages(messagesPage.getTotalPages())
                        .totalElements((int) messagesPage.getTotalElements())
                        .currentPage(messagesPage.getNumber())
                        .pageSize(messagesPage.getSize())
                        .build())
                .build();
    }

    public static SingleMessageResponseDTO toSingleResponse(Messages entity) {
        return SingleMessageResponseDTO.builder()
                .accountId(entity.getId().getAccountId())
                .conversationsId(entity.getId().getConversationsId())
                .modelChat(entity.getModelChat())
                .message(toDto(entity))
                .build();
    }

    public static SingleMessageResponseDTO toSingleResponse(Messages entity, Long childMessageId) {
        return SingleMessageResponseDTO.builder()
                .accountId(entity.getId().getAccountId())
                .childMessageId(childMessageId)
                .conversationsId(entity.getId().getConversationsId())
                .modelChat(entity.getModelChat())
                .message(toDto(entity))
                .build();
    }




}

