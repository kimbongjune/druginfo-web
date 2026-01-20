package com.nocdu.druginfo.domain.drug;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDrugSafetyInfo is a Querydsl query type for DrugSafetyInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDrugSafetyInfo extends EntityPathBase<DrugSafetyInfo> {

    private static final long serialVersionUID = 830025276L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDrugSafetyInfo drugSafetyInfo = new QDrugSafetyInfo("drugSafetyInfo");

    public final StringPath afterConsultDoctor = createString("afterConsultDoctor");

    public final StringPath allergyReaction = createString("allergyReaction");

    public final StringPath beforeConsultDoctor = createString("beforeConsultDoctor");

    public final StringPath cautionInject = createString("cautionInject");

    public final StringPath childWarning = createString("childWarning");

    public final StringPath doseWarning = createString("doseWarning");

    public final QDrug drug;

    public final StringPath durationWarning = createString("durationWarning");

    public final StringPath elderlyWarning = createString("elderlyWarning");

    public final StringPath extraCaution = createString("extraCaution");

    public final StringPath generalCaution = createString("generalCaution");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath interactionCaution = createString("interactionCaution");

    public final StringPath itemSeq = createString("itemSeq");

    public final StringPath lactationWarning = createString("lactationWarning");

    public final StringPath noInject = createString("noInject");

    public final StringPath overdoseWarning = createString("overdoseWarning");

    public final StringPath pregnantWarning = createString("pregnantWarning");

    public final StringPath storageMethod = createString("storageMethod");

    public final StringPath validTerm = createString("validTerm");

    public final StringPath warningText = createString("warningText");

    public QDrugSafetyInfo(String variable) {
        this(DrugSafetyInfo.class, forVariable(variable), INITS);
    }

    public QDrugSafetyInfo(Path<? extends DrugSafetyInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDrugSafetyInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDrugSafetyInfo(PathMetadata metadata, PathInits inits) {
        this(DrugSafetyInfo.class, metadata, inits);
    }

    public QDrugSafetyInfo(Class<? extends DrugSafetyInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.drug = inits.isInitialized("drug") ? new QDrug(forProperty("drug"), inits.get("drug")) : null;
    }

}

