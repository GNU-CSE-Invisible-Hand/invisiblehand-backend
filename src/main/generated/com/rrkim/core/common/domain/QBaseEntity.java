package com.rrkim.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseAuditEntity is a Querydsl query type for BaseAuditEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseAuditEntity extends EntityPathBase<BaseAuditEntity> {

    private static final long serialVersionUID = -1428695066L;

    public static final QBaseAuditEntity baseAuditEntity = new QBaseAuditEntity("baseAuditEntity");

    public final DateTimePath<java.time.LocalDateTime> modDt = createDateTime("modDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> modIdx = createNumber("modIdx", Long.class);

    public final StringPath modIp = createString("modIp");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> regIdx = createNumber("regIdx", Long.class);

    public final StringPath regIp = createString("regIp");

    public QBaseAuditEntity(String variable) {
        super(BaseAuditEntity.class, forVariable(variable));
    }

    public QBaseAuditEntity(Path<? extends BaseAuditEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseAuditEntity(PathMetadata metadata) {
        super(BaseAuditEntity.class, metadata);
    }

}

