package com.rrkim.module.myinfo.dto;

import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.JournalDto;
import com.rrkim.module.news.dto.data.TagDto;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {

    private List<ArticleDto> readArticles;
    private List<ArticleDto> likedNews;
    private List<ArticleDto> interestedArticles;
    private List<TagDto> interestedTags;

    public UserInfoResponseDto(List<ArticleDto> readArticles, List<ArticleDto> likedNews, List<TagDto> interestedTags) {
        this.readArticles = readArticles;
        this.likedNews = likedNews;
        this.interestedTags = interestedTags;
    }
}