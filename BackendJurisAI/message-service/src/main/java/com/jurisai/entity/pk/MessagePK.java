package com.jurisai.entity.pk;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MessagePK implements Serializable {
    private long accountId;
    private long conversationsId;
    private long messageId;
}