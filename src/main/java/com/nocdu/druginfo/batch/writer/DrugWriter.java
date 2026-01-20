package com.nocdu.druginfo.batch.writer;

import com.nocdu.druginfo.domain.drug.Drug;
import com.nocdu.druginfo.domain.drug.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 의약품 데이터 DB 저장 Writer (Upsert)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugWriter implements ItemWriter<Drug> {

    private final DrugRepository drugRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends Drug> chunk) {
        List<Drug> toSave = new ArrayList<>();

        for (Drug drug : chunk) {
            if (drug == null) {
                continue;
            }

            // 기존 데이터 확인 (Upsert)
            drugRepository.findByItemSeq(drug.getItemSeq())
                    .ifPresentOrElse(
                            existing -> {
                                // 업데이트
                                updateDrug(existing, drug);
                                toSave.add(existing);
                            },
                            () -> {
                                // 신규 추가
                                toSave.add(drug);
                            }
                    );
        }

        if (!toSave.isEmpty()) {
            List<Drug> saved = drugRepository.saveAll(toSave);
            log.debug("Saved {} drugs", saved.size());
        }
    }

    /**
     * 기존 데이터 업데이트
     */
    private void updateDrug(Drug existing, Drug newData) {
        existing.setItemName(newData.getItemName());
        existing.setEntpName(newData.getEntpName());
        existing.setEfficacy(newData.getEfficacy());
        existing.setUseMethod(newData.getUseMethod());
        existing.setCautionWarning(newData.getCautionWarning());
        existing.setCaution(newData.getCaution());
        existing.setInteraction(newData.getInteraction());
        existing.setSideEffect(newData.getSideEffect());
        existing.setStorageMethod(newData.getStorageMethod());
        existing.setImageUrl(newData.getImageUrl());
        existing.setOpenDate(newData.getOpenDate());
        existing.setUpdateDate(newData.getUpdateDate());
    }
}
