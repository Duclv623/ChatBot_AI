package com.jurisai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationResponseDTO {
    @JsonProperty("conversation_id")
    private Long conversationId;
    @JsonProperty("parent_conv_id")
    private Long parentConvId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;
}
