package com.rrkim.module.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleFile is a Querydsl query type for ArticleFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleFile extends EntityPathBase<ArticleFile> {

    private static final long serialVersionUID = -2105386362L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleFile articleFile = new QArticleFile("articleFile");

    public final com.rrkim.core.domain.QBaseAuditEntity _super = new com.rrkim.core.domain.QBaseAuditEntity(this);

    public final QArticle article;

    public final NumberPath<Long> articleFileIdx = createNumber("articleFileIdx", Long.class);

    public final com.rrkim.core.component.file.domain.QFile file;

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

    public QArticleFile(String variable) {
        this(ArticleFile.class, forVariable(variable), INITS);
    }

    public QArticleFile(Path<? extends ArticleFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleFile(PathMetadata metadata, PathInits inits) {
        this(ArticleFile.class, metadata, inits);
    }

    public QArticleFile(Class<? extends ArticleFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article"), inits.get("article")) : null;
        this.file = inits.isInitialized("file") ? new com.rrkim.core.component.file.domain.QFile(forProperty("file")) : null;
    }

}

