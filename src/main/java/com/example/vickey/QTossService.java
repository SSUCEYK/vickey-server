package com.example.vickey;

import com.example.vickey.tosspay.TossService;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.EntityPathBase;

public class QTossService extends EntityPathBase<TossService> {
    private static final long serialVersionUID = 476607969L;
    public static final QTossService tossService = new QTossService("tossService");

    public QTossService(String variable) {
        super(TossService.class, PathMetadataFactory.forVariable(variable));
    }

    public QTossService(Path<? extends TossService> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTossService(PathMetadata metadata) {
        super(TossService.class, metadata);
    }
}