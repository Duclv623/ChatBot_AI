package com.jurisai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitationResponseDTO {
    @JsonProperty("law_name")
    private String lawName;
    @JsonProperty("chapter")
    private String chapter;
    @JsonProperty("section")
    private String section;
    @JsonProperty("article")
    private String article;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;
}
