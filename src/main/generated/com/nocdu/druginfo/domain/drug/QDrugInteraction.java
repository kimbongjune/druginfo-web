package com.nocdu.druginfo.domain.drug;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDrugInteraction is a Querydsl query type for DrugInteraction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDrugInteraction extends EntityPathBase<DrugInteraction> {

    private static final long serialVersionUID = 518443510L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDrugInteraction drugInteraction = new QDrugInteraction("drugInteraction");

    public final QDrug drug;

    public final StringPath entpName = createString("entpName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemName = createString("itemName");

    public final StringPath itemSeq = createString("itemSeq");

    public final StringPath mainIngrCode = createString("mainIngrCode");

    public final StringPath mainIngrKor = createString("mainIngrKor");

    public final StringPath mixtureEntpName = createString("mixtureEntpName");

    public final StringPath mixtureItemName = createString("mixtureItemName");

    public final StringPath mixtureItemSeq = createString("mixtureItemSeq");

    public final StringPath mixtureMainIngr = createString("mixtureMainIngr");

    public final StringPath mixtureMainIngrCode = createString("mixtureMainIngrCode");

    public final StringPath prohbtContent = createString("prohbtContent");

    public final StringPath remark = createString("remark");

    public final StringPath typeCode = createString("typeCode");

    public final StringPath typeName = createString("typeName");

    public QDrugInteraction(String variable) {
        this(DrugInteraction.class, forVariable(variable), INITS);
    }

    public QDrugInteraction(Path<? extends DrugInteraction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDrugInteraction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDrugInteraction(PathMetadata metadata, PathInits inits) {
        this(DrugInteraction.class, metadata, inits);
    }

    public QDrugInteraction(Class<? extends DrugInteraction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.drug = inits.isInitialized("drug") ? new QDrug(forProperty("drug"), inits.get("drug")) : null;
    }

}

