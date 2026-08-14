package com.jurisai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListConversationResponseDTO {
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("conversations")
    private List<ConversationResponseDTO> conversations ;
    @JsonProperty("pagination")
    private PaginationDTO pagination;
}
