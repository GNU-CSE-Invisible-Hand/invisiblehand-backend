package com.rrkim.module.myinfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserInterest is a Querydsl query type for UserInterest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserInterest extends EntityPathBase<UserInterest> {

    private static final long serialVersionUID = -235978622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserInterest userInterest = new QUserInterest("userInterest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.rrkim.module.news.domain.QJournal journal;

    public final com.rrkim.module.news.domain.QNewsTag newsTag;

    public final com.rrkim.module.news.domain.QTag tag;

    public final com.rrkim.core.component.auth.domain.QUser user;

    public QUserInterest(String variable) {
        this(UserInterest.class, forVariable(variable), INITS);
    }

    public QUserInterest(Path<? extends UserInterest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserInterest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserInterest(PathMetadata metadata, PathInits inits) {
        this(UserInterest.class, metadata, inits);
    }

    public QUserInterest(Class<? extends UserInterest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.journal = inits.isInitialized("journal") ? new com.rrkim.module.news.domain.QJournal(forProperty("journal")) : null;
        this.newsTag = inits.isInitialized("newsTag") ? new com.rrkim.module.news.domain.QNewsTag(forProperty("newsTag"), inits.get("newsTag")) : null;
        this.tag = inits.isInitialized("tag") ? new com.rrkim.module.news.domain.QTag(forProperty("tag")) : null;
        this.user = inits.isInitialized("user") ? new com.rrkim.core.component.auth.domain.QUser(forProperty("user")) : null;
    }

}

