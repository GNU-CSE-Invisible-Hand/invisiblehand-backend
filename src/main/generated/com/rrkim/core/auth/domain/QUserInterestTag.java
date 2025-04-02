package com.rrkim.core.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserInterestTag is a Querydsl query type for UserInterestTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserInterestTag extends EntityPathBase<UserInterestTag> {

    private static final long serialVersionUID = 1594921406L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserInterestTag userInterestTag = new QUserInterestTag("userInterestTag");

    public final com.rrkim.core.common.domain.QBaseEntity _super = new com.rrkim.core.common.domain.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final NumberPath<Long> modIdx = _super.modIdx;

    //inherited
    public final StringPath modIp = _super.modIp;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final NumberPath<Long> regIdx = _super.regIdx;

    //inherited
    public final StringPath regIp = _super.regIp;

    public final com.rrkim.module.news.domain.QTag tag;

    public final QUser user;

    public QUserInterestTag(String variable) {
        this(UserInterestTag.class, forVariable(variable), INITS);
    }

    public QUserInterestTag(Path<? extends UserInterestTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserInterestTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserInterestTag(PathMetadata metadata, PathInits inits) {
        this(UserInterestTag.class, metadata, inits);
    }

    public QUserInterestTag(Class<? extends UserInterestTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tag = inits.isInitialized("tag") ? new com.rrkim.module.news.domain.QTag(forProperty("tag")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

