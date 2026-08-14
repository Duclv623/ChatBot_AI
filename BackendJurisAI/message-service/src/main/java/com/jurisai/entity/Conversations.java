package com.jurisai.entity;

import com.jurisai.entity.pk.ConversationPK;
import com.jurisai.entity.pk.MessagePK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "conversations", schema = "message_service")
public class Conversations {
    @EmbeddedId
    private ConversationPK id;
    @Column(name = "parent_conv_id")
    private long parentConvId;
    @Column(name = "title")
    private String title;
    @Column(name = "status")
    private int status;
    @Column(name = "created_at")
    private long createdAt;
    @Column(name = "updated_at")
    private long updatedAt;
}
