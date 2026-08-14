package com.jurisai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListCitationResponseDTO {
    @JsonProperty("message_id")
    private Long messageId;
    @JsonProperty("citations")
    private List<CitationResponseDTO> citations;

}
