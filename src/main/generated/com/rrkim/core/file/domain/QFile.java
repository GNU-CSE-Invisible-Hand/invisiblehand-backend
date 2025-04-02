package com.rrkim.core.component.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFile is a Querydsl query type for File
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFile extends EntityPathBase<File> {

    private static final long serialVersionUID = 1328654216L;

    public static final QFile file = new QFile("file");

    public final com.rrkim.core.domain.QBaseAuditEntity _super = new com.rrkim.core.domain.QBaseAuditEntity(this);

    public final NumberPath<Long> fileIdx = createNumber("fileIdx", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final NumberPath<Long> modIdx = _super.modIdx;

    //inherited
    public final StringPath modIp = _super.modIp;

    public final StringPath originFileNm = createString("originFileNm");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final NumberPath<Long> regIdx = _super.regIdx;

    //inherited
    public final StringPath regIp = _super.regIp;

    public final StringPath saveFileNm = createString("saveFileNm");

    public final StringPath savePath = createString("savePath");

    public final BooleanPath useYn = createBoolean("useYn");

    public QFile(String variable) {
        super(File.class, forVariable(variable));
    }

    public QFile(Path<? extends File> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFile(PathMetadata metadata) {
        super(File.class, metadata);
    }

}

