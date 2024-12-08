package com.example.vickey;


import com.example.vickey.entity.User;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.*;

import java.time.LocalDateTime;

public class QUser extends EntityPathBase<User> {
    private static final long serialVersionUID = 983268540L;
    private static final PathInits INITS;
    public static final QUser user;
    public final DateTimePath<LocalDateTime> createdAt;
    public final StringPath email;
    public final ArrayPath<byte[], Byte> profilePicture;
    public final QSubscription subscription;
    public final StringPath userId;

    public QUser(String variable) {
        this(User.class, PathMetadataFactory.forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.createdAt = this.createDateTime("createdAt", LocalDateTime.class);
        this.email = this.createString("email");
        this.profilePicture = this.createArray("profilePicture", byte[].class);
        this.userId = this.createString("userId");
        this.subscription = inits.isInitialized("subscription") ? new QSubscription(this.forProperty("subscription"), inits.get("subscription")) : null;
    }

    static {
        INITS = PathInits.DIRECT2;
        user = new QUser("user");
    }
}
