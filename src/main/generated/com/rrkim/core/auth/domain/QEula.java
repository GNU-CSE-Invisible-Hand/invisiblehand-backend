package com.rrkim.core.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEula is a Querydsl query type for Eula
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEula extends EntityPathBase<Eula> {

    private static final long serialVersionUID = 322585932L;

    public static final QEula eula = new QEula("eula");

    public final com.rrkim.core.common.domain.QBaseEntity _super = new com.rrkim.core.common.domain.QBaseEntity(this);

    public final StringPath content = createString("content");

    public final StringPath eulaId = createString("eulaId");

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

    public final BooleanPath requireYn = createBoolean("requireYn");

    public QEula(String variable) {
        super(Eula.class, forVariable(variable));
    }

    public QEula(Path<? extends Eula> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEula(PathMetadata metadata) {
        super(Eula.class, metadata);
    }

}

