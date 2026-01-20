package com.nocdu.druginfo.domain.drug;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDrugDurWarning is a Querydsl query type for DrugDurWarning
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDrugDurWarning extends EntityPathBase<DrugDurWarning> {

    private static final long serialVersionUID = 1337794039L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDrugDurWarning drugDurWarning = new QDrugDurWarning("drugDurWarning");

    public final QDrug drug;

    public final StringPath entpName = createString("entpName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemName = createString("itemName");

    public final StringPath itemSeq = createString("itemSeq");

    public final StringPath mainIngrCode = createString("mainIngrCode");

    public final StringPath mainIngrKor = createString("mainIngrKor");

    public final StringPath prohbtContent = createString("prohbtContent");

    public final StringPath remark = createString("remark");

    public final StringPath typeCode = createString("typeCode");

    public final StringPath typeName = createString("typeName");

    public final EnumPath<DrugDurWarning.WarningType> warningType = createEnum("warningType", DrugDurWarning.WarningType.class);

    public QDrugDurWarning(String variable) {
        this(DrugDurWarning.class, forVariable(variable), INITS);
    }

    public QDrugDurWarning(Path<? extends DrugDurWarning> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDrugDurWarning(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDrugDurWarning(PathMetadata metadata, PathInits inits) {
        this(DrugDurWarning.class, metadata, inits);
    }

    public QDrugDurWarning(Class<? extends DrugDurWarning> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.drug = inits.isInitialized("drug") ? new QDrug(forProperty("drug"), inits.get("drug")) : null;
    }

}

