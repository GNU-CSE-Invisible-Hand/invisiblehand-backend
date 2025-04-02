package com.rrkim.module.news.domain;

import com.rrkim.core.common.domain.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class Journal extends BaseEntity {

    @Column(name = "journal_id", nullable = false, length = 15, unique = true)
    private String journalId;

    @Column(name = "journal_nm", nullable = false, length = 30)
    private String journalNm;

    @Builder.Default
    @OneToMany(mappedBy = "journal", fetch = FetchType.LAZY)
    private List<News> newsList = new ArrayList<>();
}
