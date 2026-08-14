package com.jurisai.entity;

import com.jurisai.entity.pk.MessagePK;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "messages", schema = "message_service")
public class Messages {
    @EmbeddedId
    private MessagePK id;
    @Column(name = "parent_messages_id")
    private long parentMessagesId;
    @Column(name = "index_messages")
    private int indexMessages;
    @Column(name = "model_chat")
    private String modelChat;
    @Column(name = "sender")
    private int sender;
    @Column(name = "content")
    private String content;
    @Column(name = "file_uuid")
    private long fileUuid;
    @Column(name = "status")
    private int status;
    @Column(name = "created_at")
    private long createdAt;
    @Column(name = "updated_at")
    private long updatedAt;
}
