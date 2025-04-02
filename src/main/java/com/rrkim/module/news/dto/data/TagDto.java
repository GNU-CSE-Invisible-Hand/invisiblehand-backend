package com.rrkim.module.news.dto.data;

import com.rrkim.module.news.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TagDto {

    public TagDto(Tag tag) {
        this.tagIdx = tag.getId();
        this.tagId = tag.getTagId();
        this.tagName = tag.getTagName();
    }

    private Long tagIdx;

    private String tagId;

    private String tagName;
}
