package com.rrkim.module.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNewsArticle is a Querydsl query type for NewsArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNewsArticle extends EntityPathBase<NewsArticle> {

    private static final long serialVersionUID = -1329277090L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNewsArticle newsArticle = new QNewsArticle("newsArticle");

    public final com.rrkim.core.domain.QBaseAuditEntity _super = new com.rrkim.core.domain.QBaseAuditEntity(this);

    public final QArticle article;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final NumberPath<Long> modIdx = _super.modIdx;

    //inherited
    public final StringPath modIp = _super.modIp;

    public final QNews news;

    public final NumberPath<Long> newsArticleIdx = createNumber("newsArticleIdx", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final NumberPath<Long> regIdx = _super.regIdx;

    //inherited
    public final StringPath regIp = _super.regIp;

    public QNewsArticle(String variable) {
        this(NewsArticle.class, forVariable(variable), INITS);
    }

    public QNewsArticle(Path<? extends NewsArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNewsArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNewsArticle(PathMetadata metadata, PathInits inits) {
        this(NewsArticle.class, metadata, inits);
    }

    public QNewsArticle(Class<? extends NewsArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article")) : null;
        this.news = inits.isInitialized("news") ? new QNews(forProperty("news"), inits.get("news")) : null;
    }

}

