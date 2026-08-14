package com.jurisai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingleMessageResponseDTO {
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("conversations_id")
    private Long conversationsId;
    @JsonProperty("child_message_id")
    private Long childMessageId;
    @JsonProperty("model_chat")
    private String modelChat;
    @JsonProperty("message")
    private MessageResponseDTO message;
}
