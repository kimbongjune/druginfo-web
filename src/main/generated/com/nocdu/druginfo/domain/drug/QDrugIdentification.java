package com.nocdu.druginfo.domain.drug;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDrugIdentification is a Querydsl query type for DrugIdentification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDrugIdentification extends EntityPathBase<DrugIdentification> {

    private static final long serialVersionUID = -138017782L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDrugIdentification drugIdentification = new QDrugIdentification("drugIdentification");

    public final StringPath className = createString("className");

    public final StringPath classNo = createString("classNo");

    public final StringPath colorBack = createString("colorBack");

    public final StringPath colorFront = createString("colorFront");

    public final QDrug drug;

    public final StringPath drugShape = createString("drugShape");

    public final StringPath entpName = createString("entpName");

    public final StringPath etcOtcName = createString("etcOtcName");

    public final StringPath formCodeName = createString("formCodeName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath itemName = createString("itemName");

    public final StringPath itemSeq = createString("itemSeq");

    public final NumberPath<Double> lengLong = createNumber("lengLong", Double.class);

    public final NumberPath<Double> lengShort = createNumber("lengShort", Double.class);

    public final StringPath lineBack = createString("lineBack");

    public final StringPath lineFront = createString("lineFront");

    public final StringPath markCodeBack = createString("markCodeBack");

    public final StringPath markCodeFront = createString("markCodeFront");

    public final StringPath printBack = createString("printBack");

    public final StringPath printFront = createString("printFront");

    public final NumberPath<Double> thick = createNumber("thick", Double.class);

    public QDrugIdentification(String variable) {
        this(DrugIdentification.class, forVariable(variable), INITS);
    }

    public QDrugIdentification(Path<? extends DrugIdentification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDrugIdentification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDrugIdentification(PathMetadata metadata, PathInits inits) {
        this(DrugIdentification.class, metadata, inits);
    }

    public QDrugIdentification(Class<? extends DrugIdentification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.drug = inits.isInitialized("drug") ? new QDrug(forProperty("drug"), inits.get("drug")) : null;
    }

}

