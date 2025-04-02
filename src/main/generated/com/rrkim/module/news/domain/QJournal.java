package com.rrkim.module.news.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJournal is a Querydsl query type for Journal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJournal extends EntityPathBase<Journal> {

    private static final long serialVersionUID = -587299470L;

    public static final QJournal journal = new QJournal("journal");

    public final com.rrkim.core.domain.QBaseAuditEntity _super = new com.rrkim.core.domain.QBaseAuditEntity(this);

    public final StringPath journalId = createString("journalId");

    public final NumberPath<Long> journalIdx = createNumber("journalIdx", Long.class);

    public final StringPath journalName = createString("journalName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final NumberPath<Long> modIdx = _super.modIdx;

    //inherited
    public final StringPath modIp = _super.modIp;

    public final ListPath<News, QNews> newsList = this.<News, QNews>createList("newsList", News.class, QNews.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final NumberPath<Long> regIdx = _super.regIdx;

    //inherited
    public final StringPath regIp = _super.regIp;

    public QJournal(String variable) {
        super(Journal.class, forVariable(variable));
    }

    public QJournal(Path<? extends Journal> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJournal(PathMetadata metadata) {
        super(Journal.class, metadata);
    }

}

