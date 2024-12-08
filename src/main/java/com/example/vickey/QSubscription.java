package com.example.vickey;


import com.example.vickey.entity.Subscription;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.*;

import java.time.LocalDate;

public class QSubscription extends EntityPathBase<Subscription> {
    private static final long serialVersionUID = -1114150642L;
    private static final PathInits INITS;
    public static final QSubscription subscription;
    public final DatePath<LocalDate> endDate;
    public final NumberPath<Long> id;
    public final DatePath<LocalDate> startDate;
    public final EnumPath<SubscriptionType> subscriptionType;
    public final QUser user;

    public QSubscription(String variable) {
        this(Subscription.class, PathMetadataFactory.forVariable(variable), INITS);
    }

    public QSubscription(Path<? extends Subscription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubscription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubscription(PathMetadata metadata, PathInits inits) {
        this(Subscription.class, metadata, inits);
    }

    public QSubscription(Class<? extends Subscription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.endDate = this.createDate("endDate", LocalDate.class);
        this.id = this.createNumber("id", Long.class);
        this.startDate = this.createDate("startDate", LocalDate.class);
        this.subscriptionType = this.createEnum("subscriptionType", SubscriptionType.class);
        this.user = inits.isInitialized("user") ? new QUser(this.forProperty("user"), inits.get("user")) : null;
    }

    static {
        INITS = PathInits.DIRECT2;
        subscription = new QSubscription("subscription");
    }
}