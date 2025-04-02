package com.rrkim.module.news.domain;

import com.rrkim.core.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class Tag extends BaseEntity {

    @Column(name = "tag_id", nullable = false, unique = true, length = 20)
    private String tagId;

    @Column(name = "tag_name", nullable = false, length = 30)
    private String tagName;

    @Builder.Default
    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    private List<NewsTag> newsTags = new ArrayList<>();
}
