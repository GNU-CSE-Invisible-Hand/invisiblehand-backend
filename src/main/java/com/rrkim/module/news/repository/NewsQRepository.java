package com.rrkim.module.news.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.request.SelectNewsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.rrkim.module.news.domain.QNews.news;
import static com.rrkim.module.news.domain.QNewsTag.newsTag;
import static com.rrkim.module.news.domain.QTag.tag;

@RequiredArgsConstructor
@Repository
public class NewsQRepository {

    private final JPAQueryFactory queryFactory;

    public List<NewsDto> find(SelectNewsRequestDto selectNewsRequestDto) {
        return queryFactory
                .select(Projections.bean(NewsDto.class,
                    news.id.as("newsIdx"),
                    news.journal.journalId,
                    news.title,
                    news.link,
                    news.newsPhotoFile.fileId.as("photoFileId"),
                    news.publicationDate)
                )
                .from(news)
                .leftJoin(news.newsPhotoFile)
                .where(
                        inJournalIds(selectNewsRequestDto.getJournalIds()),
                        inTagIds(selectNewsRequestDto.getTagIds())
                ).fetch();
    }

    private BooleanExpression inJournalIds(List<String> journalIds) {
        if(journalIds == null || journalIds.isEmpty()) { return null; }
        return news.journal.journalId.in(journalIds);
    }

    private BooleanExpression inTagIds(List<String> tagIds) {
        if(tagIds == null || tagIds.isEmpty()) { return null; }
        return news.id.in(JPAExpressions
                .select(newsTag.news.id)
                .from(newsTag)
                .join(newsTag.tag, tag)
                .where(tag.tagId.in(tagIds))
            );
    }
}
