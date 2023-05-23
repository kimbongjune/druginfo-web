package com.nocdu.druginfo.druginfo.repository;

import com.nocdu.druginfo.druginfo.entity.DrugInfoEntity;
import com.nocdu.druginfo.request.DrugSearchRequestParamDto;
import com.nocdu.druginfo.response.Documents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 의약품 기본정보 데이터베이스 접근 JPA 인터페이스 클래스
 * TODO 배치 프로그램을 이용해 인서트 로직을 작성할 예정이다.
 */
public interface DrugInfoRepository extends JpaRepository<DrugInfoEntity, Long> {

    //아이디를 이용해 의약품 기본정보를 단건 조회한다.
    @Override
    Optional<DrugInfoEntity> findById(Long aLong);

    //의약품 api 조회 쿼리 Join을 사용하였으며, request로 받은 vo 객체를 사용하려 where 조건문을 작성하였음
    @Query("SELECT NEW com.nocdu.druginfo.response.Documents(a.id, a.entpName, a.itemName, a.itemSeq, a.efcyQesitm, a.useMethodQesitm,b.itemImage, b.className," +
            "c.warningText, c.noInject, c.cautionInject, c.allergyReaction, c.generalCaution, c.multieInjectWarning," +
            "c.pregnantWomenInjectWarning,c.lactiationWomenInjectWarning, c.childInjectWarning, c.oldmanInjectWarning," +
            " c.pregnantWomenWithLactiationWomenWarning, c.overdoseTreatment, c.doseCaution,c.beforeConsultDoctor, " +
            "c.afterConsultDoctor, c.interactionCaution, c.extraCaution, c.storageMethod, c.validTerm, c.ediCode) " +
            "FROM DrugInfoEntity as a " +
            "LEFT JOIN PillInfoEntity as b " +
            "ON a.itemSeq = b.itemSeq " +
            "LEFT JOIN DrugDetailInfoEntity as c " +
            "ON a.itemSeq = c.itemSeq " +
            "WHERE " +
            "((:#{#vo.query} IS NULL OR :#{#vo.query} = '') OR (a.itemName LIKE CONCAT('%', :#{#vo.query}, '%')) OR (a.entpName LIKE CONCAT('%', :#{#vo.query}, '%')) OR (a.efcyQesitm LIKE CONCAT('%', :#{#vo.query}, '%'))) " +
            "AND ((:#{#vo.shape} IS NULL OR :#{#vo.shape} = '') OR (b.drugShape LIKE CONCAT('%', :#{#vo.shape}, '%'))) " +
            "AND ((:#{#vo.printFront} IS NULL OR :#{#vo.printFront} = '') OR (b.printFront LIKE CONCAT('%', :#{#vo.printFront}, '%'))) " +
            "AND ((:#{#vo.printBack} IS NULL OR :#{#vo.printBack} = '') OR (b.printBack LIKE CONCAT('%', :#{#vo.printBack}, '%'))) " +
            "AND ((:#{#vo.dosageForm} IS NULL OR :#{#vo.dosageForm} = '') OR (b.formCodeName LIKE CONCAT('%', :#{#vo.dosageForm}, '%'))) " +
            "AND ((:#{#vo.colorClass} IS NULL OR :#{#vo.colorClass} = '') OR ((b.colorClass1 LIKE CONCAT('%', :#{#vo.colorClass}, '%')) OR (b.colorClass2 LIKE CONCAT('%', :#{#vo.colorClass}, '%')))) " +
            "AND ((:#{#vo.line} IS NULL OR :#{#vo.line} = '') OR ((b.lineFront LIKE CONCAT('%', :#{#vo.line}, '%')) OR (b.lineBack LIKE CONCAT('%', :#{#vo.line}, '%')))) " +
            "ORDER BY a.itemName")
    Page<Documents> selectDrugInfoSearchList(@Param("vo") DrugSearchRequestParamDto vo, Pageable pageable);
}
