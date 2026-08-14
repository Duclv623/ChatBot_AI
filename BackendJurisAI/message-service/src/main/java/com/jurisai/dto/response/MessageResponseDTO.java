package com.jurisai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jurisai.entity.pk.MessagePK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDTO {
    @JsonProperty("message_id")
    private Long messageId;
    @JsonProperty("parent_messages_id")
    private Long parentMessagesId;
    @JsonProperty("index_messages")
    private Integer indexMessages;
    @JsonProperty("sender")
    private Integer sender;
    @JsonProperty("content")
    private String content;
    @JsonProperty("file_uuid")
    private Long fileUuid;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;
}
