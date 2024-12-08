package com.example.vickey;

import com.example.vickey.entity.Episode;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

public class QEpisode extends EntityPathBase<Episode> {
    private static final long serialVersionUID = -521094294L;
    public static final QEpisode episode = new QEpisode("episode");
    public final StringPath casting = this.createString("casting");
    public final StringPath description = this.createString("description");
    public final NumberPath<Integer> episodeId = this.createNumber("episodeId", Integer.class);
    public final NumberPath<Integer> episodeNum = this.createNumber("episodeNum", Integer.class);
    public final StringPath releaseDate = this.createString("releaseDate");
    public final StringPath thumbnailUrl = this.createString("thumbnailUrl");
    public final StringPath title = this.createString("title");
    public final StringPath videoURLs = this.createString("videoURLs");

    public QEpisode(String variable) {
        super(Episode.class, PathMetadataFactory.forVariable(variable));
    }

    public QEpisode(Path<? extends Episode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEpisode(PathMetadata metadata) {
        super(Episode.class, metadata);
    }
}