package com.rrkim.module.myinfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserLikedNews is a Querydsl query type for UserLikedNews
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserLikedNews extends EntityPathBase<UserLikedNews> {

    private static final long serialVersionUID = 611857864L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserLikedNews userLikedNews = new QUserLikedNews("userLikedNews");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.rrkim.module.board.entity.QSummaryNewsEntity news;

    public final com.rrkim.core.component.auth.domain.QUser user;

    public QUserLikedNews(String variable) {
        this(UserLikedNews.class, forVariable(variable), INITS);
    }

    public QUserLikedNews(Path<? extends UserLikedNews> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserLikedNews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserLikedNews(PathMetadata metadata, PathInits inits) {
        this(UserLikedNews.class, metadata, inits);
    }

    public QUserLikedNews(Class<? extends UserLikedNews> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.news = inits.isInitialized("news") ? new com.rrkim.module.board.entity.QSummaryNewsEntity(forProperty("news"), inits.get("news")) : null;
        this.user = inits.isInitialized("user") ? new com.rrkim.core.component.auth.domain.QUser(forProperty("user")) : null;
    }

}

