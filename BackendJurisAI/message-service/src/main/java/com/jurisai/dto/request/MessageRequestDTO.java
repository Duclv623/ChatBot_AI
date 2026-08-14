package com.jurisai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestDTO {
    @JsonProperty("parent_messages_id")
    private Long parentMessagesId;
    @JsonProperty("model_chat")
    private String modelChat;
    @JsonProperty("sender")
    private Integer sender;
    @JsonProperty("content")
    private String content;
    @JsonProperty("file_uuid")
    private Long fileUuid;
    @JsonProperty("status")
    private Integer status;
}
