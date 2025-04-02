package com.rrkim.module.news.service;

import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.domain.Keyword;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.KeywordDto;
import com.rrkim.module.news.dto.request.*;
import com.rrkim.module.news.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.security.Key;
import java.util.List;

@Transactional(readOnly = true)
@Validated
@RequiredArgsConstructor
@Service
public class KeywordService {

    private final KeywordQRepository keywordQRepository;
    private final KeywordRepository keywordRepository;
    private final ArticleQRepository articleQRepository;
    private final ArticleRepository articleRepository;

    public List<KeywordDto> selectKeywordList(SelectKeywordRequestDto selectKeywordRequestDto) {
        return keywordQRepository.find(selectKeywordRequestDto);
    }

    @Transactional
    public KeywordDto createKeyword(@Valid CreateKeywordRequestDto createKeywordRequestDto) {
        Keyword keyword = Keyword.builder()
                .content(createKeywordRequestDto.getKeyword())
                .build();

        keywordRepository.save(keyword);
        return new KeywordDto(keyword);
    }

    @Transactional
    public KeywordDto mappingKeyword(@Valid MappingArticleKeywordRequestDto mappingArticleKeywordRequestDto) {
        Long keywordIdx = mappingArticleKeywordRequestDto.getKeywordIdx();
        List<Long> articleIdxes = mappingArticleKeywordRequestDto.getArticleIdxes();

        Keyword keyword = keywordQRepository.find(keywordIdx);
        if(keyword == null) {
            throw new UnhandledExecutionException("news.keywordNotFound");
        }

        List<Article> articles = articleQRepository.find(articleIdxes);
        if(articles != null && !articles.isEmpty()) {
            keyword.addArticle(articles);
        }

        return new KeywordDto(keyword);
    }

    @Transactional
    public KeywordDto unmappingKeyword(@Valid MappingArticleKeywordRequestDto mappingArticleKeywordRequestDto) {
        Long keywordIdx = mappingArticleKeywordRequestDto.getKeywordIdx();
        List<Long> articleIdxes = mappingArticleKeywordRequestDto.getArticleIdxes();

        Keyword keyword = keywordRepository.findKeywordById(keywordIdx);
        List<Article> articles = articleRepository.findArticleByIdIn(articleIdxes);
        if(articles != null && !articles.isEmpty()) {
            keyword.removeArticle(articles);
        }

        return new KeywordDto(keyword);
    }
}
