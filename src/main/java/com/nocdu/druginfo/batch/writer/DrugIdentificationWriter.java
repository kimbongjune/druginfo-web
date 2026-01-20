package com.nocdu.druginfo.batch.writer;

import com.nocdu.druginfo.domain.drug.Drug;
import com.nocdu.druginfo.domain.drug.DrugIdentification;
import com.nocdu.druginfo.domain.drug.repository.DrugIdentificationRepository;
import com.nocdu.druginfo.domain.drug.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DrugIdentification 엔티티 DB 저장 Writer
 * - Upsert 로직: 기존 데이터가 있으면 업데이트, 없으면 삽입
 * - Drug 엔티티와 연관관계 설정
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugIdentificationWriter implements ItemWriter<DrugIdentification> {

    private final DrugIdentificationRepository drugIdentificationRepository;
    private final DrugRepository drugRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends DrugIdentification> chunk) throws Exception {
        log.debug("Writing {} drug identification items", chunk.size());

        for (DrugIdentification identification : chunk) {
            try {
                // Drug 엔티티와 연관관계 설정 (존재하는 경우)
                Optional<Drug> drugOpt = drugRepository.findByItemSeq(identification.getItemSeq());
                drugOpt.ifPresent(identification::setDrug);

                // Upsert: itemSeq로 기존 데이터 확인
                Optional<DrugIdentification> existing = 
                        drugIdentificationRepository.findByItemSeq(identification.getItemSeq());

                if (existing.isPresent()) {
                    // 기존 데이터 업데이트
                    DrugIdentification existingId = existing.get();
                    updateExisting(existingId, identification);
                    drugIdentificationRepository.save(existingId);
                    log.trace("Updated drug identification: {}", identification.getItemSeq());
                } else {
                    // 신규 데이터 삽입
                    drugIdentificationRepository.save(identification);
                    log.trace("Inserted drug identification: {}", identification.getItemSeq());
                }
            } catch (Exception e) {
                log.error("Error writing drug identification {}: {}", 
                        identification.getItemSeq(), e.getMessage());
                // 개별 아이템 실패는 무시하고 계속 진행
            }
        }
    }

    private void updateExisting(DrugIdentification existing, DrugIdentification newData) {
        existing.setItemName(newData.getItemName());
        existing.setEntpName(newData.getEntpName());
        existing.setDrugShape(newData.getDrugShape());
        existing.setColorFront(newData.getColorFront());
        existing.setColorBack(newData.getColorBack());
        existing.setFormCodeName(newData.getFormCodeName());
        existing.setLineFront(newData.getLineFront());
        existing.setLineBack(newData.getLineBack());
        existing.setPrintFront(newData.getPrintFront());
        existing.setPrintBack(newData.getPrintBack());
        existing.setImageUrl(newData.getImageUrl());
        existing.setMarkCodeFront(newData.getMarkCodeFront());
        existing.setMarkCodeBack(newData.getMarkCodeBack());
        existing.setLengLong(newData.getLengLong());
        existing.setLengShort(newData.getLengShort());
        existing.setThick(newData.getThick());
        existing.setClassName(newData.getClassName());
        existing.setClassNo(newData.getClassNo());
        existing.setEtcOtcName(newData.getEtcOtcName());
        
        // Drug 연관관계 업데이트
        if (newData.getDrug() != null) {
            existing.setDrug(newData.getDrug());
        }
    }
}
