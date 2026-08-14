package com.jurisai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="accounts")
public class Account {
    @Id
    private Long id;

    @Email
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name = "provider")
    private Integer provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "is_actived")
    private Integer isActived;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
