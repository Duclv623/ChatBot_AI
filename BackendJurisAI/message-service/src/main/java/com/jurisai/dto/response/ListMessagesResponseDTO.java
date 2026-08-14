package com.jurisai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListMessagesResponseDTO {
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("conversations_id")
    private Long conversationsId;
    @JsonProperty("model_chat")
    private String modelChat;
    @JsonProperty("messages")
    private List<MessageResponseDTO> messages ;
    @JsonProperty("pagination")
    private PaginationDTO pagination;
}
