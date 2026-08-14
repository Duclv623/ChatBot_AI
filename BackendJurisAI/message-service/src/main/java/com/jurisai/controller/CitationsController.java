package com.jurisai.controller;

import com.jurisai.dto.response.CustomResponse;
import com.jurisai.dto.response.ListCitationResponseDTO;
import com.jurisai.entity.Citations;
import com.jurisai.mapper.CitationMapper;
import com.jurisai.service.CitationsService;
import com.jurisai.service.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/message-service/{accountId}")
@RequiredArgsConstructor
public class CitationsController {
    private final CitationsService citationsService;
    @GetMapping("/citations/{messageId}")
    public CustomResponse<ListCitationResponseDTO> getCitations ( @PathVariable("accountId") Long accountId,
                                                                  @PathVariable("messageId") Long messageId
    ) {
        // get list citations
        List<Citations> citations = citationsService.getList(accountId, messageId);
        if(citations == null) {
            return new CustomResponse<>(false, "Not found citations!");
        }
        // build response dto
        ListCitationResponseDTO res = CitationMapper.toListResponse(citations);
        return new CustomResponse<>(res, "Get list citations");
    }
}
