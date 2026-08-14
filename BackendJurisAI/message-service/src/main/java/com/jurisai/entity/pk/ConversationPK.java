package com.jurisai.entity.pk;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ConversationPK implements Serializable {
    private long accountId;
    private long conversationsId;
}
