package com.rrkim.core.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEula is a Querydsl query type for UserEula
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEula extends EntityPathBase<UserEula> {

    private static final long serialVersionUID = -1071391689L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserEula userEula = new QUserEula("userEula");

    public final com.rrkim.core.common.domain.QBaseEntity _super = new com.rrkim.core.common.domain.QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> agreeDate = createDateTime("agreeDate", java.time.LocalDateTime.class);

    public final QEula eula;

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

    public final QUser user;

    public QUserEula(String variable) {
        this(UserEula.class, forVariable(variable), INITS);
    }

    public QUserEula(Path<? extends UserEula> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserEula(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserEula(PathMetadata metadata, PathInits inits) {
        this(UserEula.class, metadata, inits);
    }

    public QUserEula(Class<? extends UserEula> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.eula = inits.isInitialized("eula") ? new QEula(forProperty("eula")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

