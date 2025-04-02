package com.rrkim.module.myinfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserReadArticle is a Querydsl query type for UserReadArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserReadArticle extends EntityPathBase<UserReadArticle> {

    private static final long serialVersionUID = 1672216104L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserReadArticle userReadArticle = new QUserReadArticle("userReadArticle");

    public final com.rrkim.module.board.entity.QSummaryNewsEntity article;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.rrkim.core.component.auth.domain.QUser user;

    public QUserReadArticle(String variable) {
        this(UserReadArticle.class, forVariable(variable), INITS);
    }

    public QUserReadArticle(Path<? extends UserReadArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserReadArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserReadArticle(PathMetadata metadata, PathInits inits) {
        this(UserReadArticle.class, metadata, inits);
    }

    public QUserReadArticle(Class<? extends UserReadArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new com.rrkim.module.board.entity.QSummaryNewsEntity(forProperty("article"), inits.get("article")) : null;
        this.user = inits.isInitialized("user") ? new com.rrkim.core.component.auth.domain.QUser(forProperty("user")) : null;
    }

}

