package com.nocdu.druginfo.domain.drug;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDrugIngredient is a Querydsl query type for DrugIngredient
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDrugIngredient extends EntityPathBase<DrugIngredient> {

    private static final long serialVersionUID = 720658797L;

    public static final QDrugIngredient drugIngredient = new QDrugIngredient("drugIngredient");

    public final StringPath admRoute = createString("admRoute");

    public final StringPath atcCode = createString("atcCode");

    public final StringPath atcName = createString("atcName");

    public final StringPath componentCode = createString("componentCode");

    public final StringPath componentEngName = createString("componentEngName");

    public final StringPath componentKorName = createString("componentKorName");

    public final StringPath formulaCode = createString("formulaCode");

    public final StringPath formulaName = createString("formulaName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDrugIngredient(String variable) {
        super(DrugIngredient.class, forVariable(variable));
    }

    public QDrugIngredient(Path<? extends DrugIngredient> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDrugIngredient(PathMetadata metadata) {
        super(DrugIngredient.class, metadata);
    }

}

