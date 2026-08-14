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
public class ImagePK implements Serializable {
    private Long accountId;
    private Long imageId;
}
