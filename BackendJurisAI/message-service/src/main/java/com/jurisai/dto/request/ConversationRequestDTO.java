package com.jurisai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationRequestDTO {
    @JsonProperty("title")
    private String title;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("parent_conv_id")
    private Long parentConvId;
}