package com.rrkim.module.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNewsTag is a Querydsl query type for NewsTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNewsTag extends EntityPathBase<NewsTag> {

    private static final long serialVersionUID = -1616691710L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNewsTag newsTag = new QNewsTag("newsTag");

    public final com.rrkim.core.domain.QBaseAuditEntity _super = new com.rrkim.core.domain.QBaseAuditEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final NumberPath<Long> modIdx = _super.modIdx;

    //inherited
    public final StringPath modIp = _super.modIp;

    public final QNews news;

    public final NumberPath<Long> newsTagIdx = createNumber("newsTagIdx", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final NumberPath<Long> regIdx = _super.regIdx;

    //inherited
    public final StringPath regIp = _super.regIp;

    public final QTag tag;

    public QNewsTag(String variable) {
        this(NewsTag.class, forVariable(variable), INITS);
    }

    public QNewsTag(Path<? extends NewsTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNewsTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNewsTag(PathMetadata metadata, PathInits inits) {
        this(NewsTag.class, metadata, inits);
    }

    public QNewsTag(Class<? extends NewsTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.news = inits.isInitialized("news") ? new QNews(forProperty("news"), inits.get("news")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

