package com.rrkim.module.notification.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.news.domain.Article;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.notification.constant.NotificationStatus;
import com.rrkim.module.notification.domain.NotificationHistory;
import com.rrkim.module.notification.dto.data.NotificationHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rrkim.module.news.domain.QArticle.article;
import static com.rrkim.module.news.domain.QArticleKeyword.articleKeyword;
import static com.rrkim.module.news.domain.QKeyword.keyword;
import static com.rrkim.module.news.domain.QNews.news;
import static com.rrkim.module.notification.domain.QNotificationHistory.notificationHistory;

@RequiredArgsConstructor
@Repository
public class NotificationHistoryQRepository {

    private final JPAQueryFactory queryFactory;

    public List<NotificationHistoryDto> getNotificationHistory(String topic) {
        return queryFactory
                .select(notificationHistory)
                .from(notificationHistory)
                .where(
                        eqTopic(topic),
                        notificationHistory.notificationStatus.eq(NotificationStatus.SUCCESS)
                )
                .orderBy(notificationHistory.id.desc())
                .limit(10)
                .transform(GroupBy.groupBy(notificationHistory.id).list(
                        Projections.bean(NotificationHistoryDto.class,
                                        notificationHistory.id.as("notificationHistoryIdx"),
                                        notificationHistory.title,
                                        notificationHistory.content,
                                        notificationHistory.topic,
                                        notificationHistory.dataJson
                                )
                ));
    }

    private static BooleanExpression eqTopic(String topic) {
        if(topic == null) return null;
        return notificationHistory.topic.eq(topic);
    }

}
