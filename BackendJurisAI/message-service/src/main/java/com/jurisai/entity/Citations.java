package com.jurisai.entity;

import com.jurisai.entity.pk.CitationPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "citations", schema = "message_service")
public class Citations {
    @EmbeddedId
    private CitationPK id;
    @Column(name = "law_name")
    private String lawName;
    @Column(name = "chapter")
    private String chapter;
    @Column(name = "section")
    private String section;
    @Column(name = "article")
    private String article;
    @Column(name = "created_at")
    private Long createdAt;
    @Column(name = "updated_at")
    private Long updatedAt;
}
