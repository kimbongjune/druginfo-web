package com.nocdu.druginfo.domain.drug;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDrug is a Querydsl query type for Drug
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDrug extends EntityPathBase<Drug> {

    private static final long serialVersionUID = 567213692L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDrug drug = new QDrug("drug");

    public final StringPath caution = createString("caution");

    public final StringPath cautionWarning = createString("cautionWarning");

    public final StringPath efficacy = createString("efficacy");

    public final StringPath entpName = createString("entpName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QDrugIdentification identification;

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath interaction = createString("interaction");

    public final StringPath itemName = createString("itemName");

    public final StringPath itemSeq = createString("itemSeq");

    public final DatePath<java.time.LocalDate> openDate = createDate("openDate", java.time.LocalDate.class);

    public final QDrugSafetyInfo safetyInfo;

    public final StringPath sideEffect = createString("sideEffect");

    public final StringPath storageMethod = createString("storageMethod");

    public final DatePath<java.time.LocalDate> updateDate = createDate("updateDate", java.time.LocalDate.class);

    public final StringPath useMethod = createString("useMethod");

    public QDrug(String variable) {
        this(Drug.class, forVariable(variable), INITS);
    }

    public QDrug(Path<? extends Drug> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDrug(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDrug(PathMetadata metadata, PathInits inits) {
        this(Drug.class, metadata, inits);
    }

    public QDrug(Class<? extends Drug> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.identification = inits.isInitialized("identification") ? new QDrugIdentification(forProperty("identification"), inits.get("identification")) : null;
        this.safetyInfo = inits.isInitialized("safetyInfo") ? new QDrugSafetyInfo(forProperty("safetyInfo"), inits.get("safetyInfo")) : null;
    }

}

