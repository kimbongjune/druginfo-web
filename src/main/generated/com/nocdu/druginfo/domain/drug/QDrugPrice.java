package com.nocdu.druginfo.domain.drug;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDrugPrice is a Querydsl query type for DrugPrice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDrugPrice extends EntityPathBase<DrugPrice> {

    private static final long serialVersionUID = 370503725L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDrugPrice drugPrice = new QDrugPrice("drugPrice");

    public final StringPath applyEndDate = createString("applyEndDate");

    public final StringPath applyStartDate = createString("applyStartDate");

    public final QDrug drug;

    public final NumberPath<java.math.BigDecimal> drugAmount = createNumber("drugAmount", java.math.BigDecimal.class);

    public final StringPath ediCode = createString("ediCode");

    public final StringPath entpName = createString("entpName");

    public final StringPath gnlNmCd = createString("gnlNmCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemName = createString("itemName");

    public final StringPath itemSeq = createString("itemSeq");

    public final NumberPath<java.math.BigDecimal> maxPrice = createNumber("maxPrice", java.math.BigDecimal.class);

    public final StringPath payType = createString("payType");

    public final StringPath spec = createString("spec");

    public final StringPath unit = createString("unit");

    public QDrugPrice(String variable) {
        this(DrugPrice.class, forVariable(variable), INITS);
    }

    public QDrugPrice(Path<? extends DrugPrice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDrugPrice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDrugPrice(PathMetadata metadata, PathInits inits) {
        this(DrugPrice.class, metadata, inits);
    }

    public QDrugPrice(Class<? extends DrugPrice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.drug = inits.isInitialized("drug") ? new QDrug(forProperty("drug"), inits.get("drug")) : null;
    }

}

