package com.jurisai.controller;

import com.jurisai.dto.request.ConversationRequestDTO;
import com.jurisai.dto.response.*;
import com.jurisai.entity.Conversations;
import com.jurisai.entity.Messages;
import com.jurisai.entity.pk.ConversationPK;
import com.jurisai.mapper.ConversationMapper;
import com.jurisai.mapper.MessageMapper;
import com.jurisai.service.ConversationsService;
import com.jurisai.util.ConversationConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/v1/message-service/{accountId}")
@RequiredArgsConstructor
public class ConversationsController {
    private final ConversationsService conversationsService;

    @PostMapping("/conversations")
    public CustomResponse<SingleConversationResponseDTO> addConversation( @RequestBody ConversationRequestDTO body,
                                                                          @PathVariable("accountId") Long accountId
    ){
        String title = body.getTitle();
        Long parentConvId = body.getParentConvId();

        // check last conversation of accountId has status != 0
        boolean checkParentConv = conversationsService.checkParentConv(accountId, parentConvId);
        if(!checkParentConv) {
            return new CustomResponse<>(null, "Current conv is blank or not found parent conv by ID!");
        }

        // gen ConversationKey
        ConversationPK pk = conversationsService.genConversationPK(accountId);

        // create new conversation
        long timestamp = new Date().getTime();
        Conversations conversation = new Conversations(pk, parentConvId, title,
                ConversationConstant.ConversationStatus.OPEN.getValue(), timestamp, timestamp);

        // save Conversation
        conversationsService.addOrUpdate(conversation);

        // build Message response
        SingleConversationResponseDTO res = ConversationMapper.toSingleResponse(conversation);
        return new CustomResponse<>(res, "create new conv success");
    }
    @GetMapping("/conversations")
    public CustomResponse<ListConversationResponseDTO> getConversations(@PathVariable("accountId") Long accountId,
                                                                        @RequestParam(required = false, defaultValue = "20") Integer size,
                                                                        @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                        @RequestParam(required = false, defaultValue = "updatedAt") String sortBy
    ){
        // check accountId

        Page<Conversations> conversations = conversationsService.findListConv(size, page, sortBy, accountId);
        // check conversations
        if (conversations == null){
            return new CustomResponse<>(null, "Not found conversations!");
        }


        // build Conv response
        ListConversationResponseDTO res = ConversationMapper.toListResponse(conversations);
        return new CustomResponse<>(res, "Get conversations success!");
    }
    @GetMapping("/conversations/{conversationId}")
    public CustomResponse<SingleConversationResponseDTO> getConversation(@PathVariable("accountId") Long accountId,
                                                                         @PathVariable("conversationId") Long conversationId
    ){
        // check accountId

        // get Conversation
        Conversations conversation = conversationsService.findByPimaryKey(accountId, conversationId);
        if (conversation == null) {
            // should replace by data exception
            return new CustomResponse<>(null, "Not found conversation by id!");
        }

        // build Conv response
        SingleConversationResponseDTO res = ConversationMapper.toSingleResponse(conversation);
        return new CustomResponse<>(res, "Get Conversation success!");
    }
    @PutMapping("/conversations/{conversationId}")
    public CustomResponse<SingleConversationResponseDTO> updateConversation(@RequestBody ConversationRequestDTO body,
                                                                            @PathVariable("accountId") Long accountId,
                                                                            @PathVariable("conversationId") Long conversationId
    ){
        // check accountId

        // check conversationId
        Conversations conversation = conversationsService.findByPimaryKey(accountId, conversationId);
        if (conversation == null) {
            // should replace by data exception
            return new CustomResponse<>(null, "Not found conversation by id!");
        }

        // update conversation
        long timestamp = new Date().getTime();
        conversation.setTitle(body.getTitle());
        conversation.setUpdatedAt(timestamp);

        // save conversation
        conversationsService.addOrUpdate(conversation);
        // build Conv response
        SingleConversationResponseDTO res = ConversationMapper.toSingleResponse(conversation);

        return new CustomResponse<>(res, "Update Conversation Success");
    }
    @DeleteMapping("/conversations/{conversationId}")
    public CustomResponse<Void> deleteConversationById(@PathVariable("accountId") Long accountId,
                                                       @PathVariable("conversationId") Long conversationId
    ) {
        try {
            // check  conversation
            boolean deleted = conversationsService.deleteConversation(accountId, conversationId);

            if (deleted) {
                return new CustomResponse<>(true, "Conversation deleted successfully");
            } else {
                return new CustomResponse<>(false, "Failed to delete conversation or conversation not found.");
            }
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return new CustomResponse<>(false, "Cannot delete conversation due to existing related data.");
        } catch (Exception e) {
            System.err.println("Error deleting conversation: " + e.getMessage());
            return new CustomResponse<>(false, "An unexpected error occurred: " + e.getMessage());
        }
    }

}
