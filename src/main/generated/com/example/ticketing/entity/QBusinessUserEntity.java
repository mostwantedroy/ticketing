package com.example.ticketing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBusinessUserEntity is a Querydsl query type for BusinessUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusinessUserEntity extends EntityPathBase<BusinessUserEntity> {

    private static final long serialVersionUID = -1661252240L;

    public static final QBusinessUserEntity businessUserEntity = new QBusinessUserEntity("businessUserEntity");

    public final StringPath businessLicense = createString("businessLicense");

    public final NumberPath<Long> businessUserId = createNumber("businessUserId", Long.class);

    public final EnumPath<com.example.ticketing.type.BusinessUserType> businessUserType = createEnum("businessUserType", com.example.ticketing.type.BusinessUserType.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = createDateTime("lastModifiedAt", java.time.LocalDateTime.class);

    public final StringPath password = createString("password");

    public QBusinessUserEntity(String variable) {
        super(BusinessUserEntity.class, forVariable(variable));
    }

    public QBusinessUserEntity(Path<? extends BusinessUserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBusinessUserEntity(PathMetadata metadata) {
        super(BusinessUserEntity.class, metadata);
    }

}

