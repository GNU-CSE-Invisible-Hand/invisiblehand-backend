package com.rrkim.module.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNews is a Querydsl query type for News
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNews extends EntityPathBase<News> {

    private static final long serialVersionUID = 341628536L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNews news = new QNews("news");

    public final com.rrkim.core.domain.QBaseAuditEntity _super = new com.rrkim.core.domain.QBaseAuditEntity(this);

    public final QJournal journal;

    public final StringPath link = createString("link");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final NumberPath<Long> modIdx = _super.modIdx;

    //inherited
    public final StringPath modIp = _super.modIp;

    public final ListPath<NewsArticle, QNewsArticle> newsArticles = this.<NewsArticle, QNewsArticle>createList("newsArticles", NewsArticle.class, QNewsArticle.class, PathInits.DIRECT2);

    public final NumberPath<Long> newsIdx = createNumber("newsIdx", Long.class);

    public final ListPath<NewsTag, QNewsTag> newsTags = this.<NewsTag, QNewsTag>createList("newsTags", NewsTag.class, QNewsTag.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> publicationDate = createDateTime("publicationDate", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final NumberPath<Long> regIdx = _super.regIdx;

    //inherited
    public final StringPath regIp = _super.regIp;

    public final StringPath title = createString("title");

    public QNews(String variable) {
        this(News.class, forVariable(variable), INITS);
    }

    public QNews(Path<? extends News> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNews(PathMetadata metadata, PathInits inits) {
        this(News.class, metadata, inits);
    }

    public QNews(Class<? extends News> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.journal = inits.isInitialized("journal") ? new QJournal(forProperty("journal")) : null;
    }

}

