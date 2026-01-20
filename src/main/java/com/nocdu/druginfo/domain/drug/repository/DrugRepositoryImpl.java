package com.nocdu.druginfo.domain.drug.repository;

import com.nocdu.druginfo.api.drug.request.DrugSearchRequest;
import com.nocdu.druginfo.api.drug.request.PillSearchRequest;
import com.nocdu.druginfo.api.drug.response.DrugSearchResponse;
import com.nocdu.druginfo.domain.drug.Drug;
import com.nocdu.druginfo.domain.drug.QDrug;
import com.nocdu.druginfo.domain.drug.QDrugIdentification;
import com.nocdu.druginfo.domain.drug.QDrugSafetyInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * QueryDSL 기반 의약품 검색 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class DrugRepositoryImpl implements DrugRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QDrug drug = QDrug.drug;
    private static final QDrugIdentification ident = QDrugIdentification.drugIdentification;
    private static final QDrugSafetyInfo safety = QDrugSafetyInfo.drugSafetyInfo;

    @Override
    public Page<DrugSearchResponse> searchDrugs(DrugSearchRequest request, Pageable pageable) {
        // 동적 쿼리 조건 생성
        BooleanBuilder whereClause = new BooleanBuilder();

        // 통합 검색어 (의약품명, 제조사, 효능 OR 조건)
        if (StringUtils.hasText(request.getQuery())) {
            String query = request.getQuery();
            whereClause.and(
                drug.itemName.containsIgnoreCase(query)
                    .or(drug.entpName.containsIgnoreCase(query))
                    .or(drug.efficacy.containsIgnoreCase(query))
            );
        }

        // 개별 검색 조건
        whereClause.and(containsIfNotEmpty(drug.itemName, request.getItemName()));
        whereClause.and(containsIfNotEmpty(drug.entpName, request.getEntpName()));
        whereClause.and(containsIfNotEmpty(drug.efficacy, request.getEfficacy()));
        whereClause.and(containsIfNotEmpty(ident.className, request.getClassName()));
        whereClause.and(eqIfNotEmpty(ident.etcOtcName, request.getEtcOtcName()));

        // 결과 조회
        List<DrugSearchResponse> content = queryFactory
                .select(Projections.constructor(DrugSearchResponse.class,
                        drug.id, drug.itemSeq, drug.itemName, drug.entpName,
                        drug.efficacy, drug.useMethod, drug.imageUrl,
                        ident.drugShape, ident.colorFront, ident.colorBack,
                        ident.formCodeName, ident.className, ident.etcOtcName,
                        ident.printFront, ident.printBack,
                        drug.cautionWarning, drug.sideEffect, drug.interaction,
                        drug.storageMethod,
                        safety.pregnantWarning, safety.lactationWarning,
                        safety.childWarning, safety.elderlyWarning, safety.overdoseWarning
                ))
                .from(drug)
                .leftJoin(drug.identification, ident)
                .leftJoin(drug.safetyInfo, safety)
                .where(whereClause)
                .orderBy(drug.itemName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 카운트 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(drug.count())
                .from(drug)
                .leftJoin(drug.identification, ident)
                .where(whereClause);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<DrugSearchResponse> searchByIdentification(PillSearchRequest request, Pageable pageable) {
        BooleanBuilder whereClause = new BooleanBuilder();

        // 모양 검색
        whereClause.and(containsIfNotEmpty(ident.drugShape, request.getShape()));

        // 색상 검색 (통합 또는 개별)
        if (StringUtils.hasText(request.getColor())) {
            whereClause.and(
                ident.colorFront.containsIgnoreCase(request.getColor())
                    .or(ident.colorBack.containsIgnoreCase(request.getColor()))
            );
        }
        whereClause.and(containsIfNotEmpty(ident.colorFront, request.getColorFront()));
        whereClause.and(containsIfNotEmpty(ident.colorBack, request.getColorBack()));

        // 식별문구 검색
        whereClause.and(containsIfNotEmpty(ident.printFront, request.getPrintFront()));
        whereClause.and(containsIfNotEmpty(ident.printBack, request.getPrintBack()));

        // 제형 검색
        whereClause.and(containsIfNotEmpty(ident.formCodeName, request.getFormCodeName()));

        // 분할선 검색
        if (StringUtils.hasText(request.getLine())) {
            whereClause.and(
                ident.lineFront.containsIgnoreCase(request.getLine())
                    .or(ident.lineBack.containsIgnoreCase(request.getLine()))
            );
        }

        // 분류명 검색
        whereClause.and(containsIfNotEmpty(ident.className, request.getClassName()));

        // 결과 조회
        List<DrugSearchResponse> content = queryFactory
                .select(Projections.constructor(DrugSearchResponse.class,
                        drug.id, drug.itemSeq, drug.itemName, drug.entpName,
                        drug.efficacy, drug.useMethod, drug.imageUrl,
                        ident.drugShape, ident.colorFront, ident.colorBack,
                        ident.formCodeName, ident.className, ident.etcOtcName,
                        ident.printFront, ident.printBack,
                        drug.cautionWarning, drug.sideEffect, drug.interaction,
                        drug.storageMethod,
                        safety.pregnantWarning, safety.lactationWarning,
                        safety.childWarning, safety.elderlyWarning, safety.overdoseWarning
                ))
                .from(drug)
                .innerJoin(drug.identification, ident)
                .leftJoin(drug.safetyInfo, safety)
                .where(whereClause)
                .orderBy(drug.itemName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 카운트 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(drug.count())
                .from(drug)
                .innerJoin(drug.identification, ident)
                .where(whereClause);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    /**
     * 문자열이 비어있지 않으면 LIKE 조건 생성
     */
    private BooleanExpression containsIfNotEmpty(com.querydsl.core.types.dsl.StringPath path, String value) {
        return StringUtils.hasText(value) ? path.containsIgnoreCase(value) : null;
    }

    /**
     * 문자열이 비어있지 않으면 EQ 조건 생성
     */
    private BooleanExpression eqIfNotEmpty(com.querydsl.core.types.dsl.StringPath path, String value) {
        return StringUtils.hasText(value) ? path.eq(value) : null;
    }
}
