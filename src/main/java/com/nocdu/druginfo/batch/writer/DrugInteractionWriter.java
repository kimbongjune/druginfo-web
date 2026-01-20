package com.nocdu.druginfo.batch.writer;

import com.nocdu.druginfo.domain.drug.Drug;
import com.nocdu.druginfo.domain.drug.DrugInteraction;
import com.nocdu.druginfo.domain.drug.repository.DrugInteractionRepository;
import com.nocdu.druginfo.domain.drug.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DrugInteraction 엔티티 DB 저장 Writer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugInteractionWriter implements ItemWriter<DrugInteraction> {

    private final DrugInteractionRepository drugInteractionRepository;
    private final DrugRepository drugRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends DrugInteraction> chunk) throws Exception {
        log.debug("Writing {} drug interaction items", chunk.size());

        for (DrugInteraction interaction : chunk) {
            try {
                // Drug 엔티티와 연관관계 설정
                Optional<Drug> drugOpt = drugRepository.findByItemSeq(interaction.getItemSeq());
                drugOpt.ifPresent(interaction::setDrug);

                // Upsert
                Optional<DrugInteraction> existing = drugInteractionRepository
                        .findByItemSeqAndMixtureItemSeq(interaction.getItemSeq(), interaction.getMixtureItemSeq());

                if (existing.isPresent()) {
                    DrugInteraction e = existing.get();
                    e.setItemName(interaction.getItemName());
                    e.setEntpName(interaction.getEntpName());
                    e.setMainIngrCode(interaction.getMainIngrCode());
                    e.setMainIngrKor(interaction.getMainIngrKor());
                    e.setMixtureItemName(interaction.getMixtureItemName());
                    e.setMixtureEntpName(interaction.getMixtureEntpName());
                    e.setMixtureMainIngrCode(interaction.getMixtureMainIngrCode());
                    e.setMixtureMainIngr(interaction.getMixtureMainIngr());
                    e.setProhbtContent(interaction.getProhbtContent());
                    e.setRemark(interaction.getRemark());
                    e.setTypeCode(interaction.getTypeCode());
                    e.setTypeName(interaction.getTypeName());
                    if (interaction.getDrug() != null) {
                        e.setDrug(interaction.getDrug());
                    }
                    drugInteractionRepository.save(e);
                } else {
                    drugInteractionRepository.save(interaction);
                }
            } catch (Exception e) {
                log.error("Error writing drug interaction {}: {}", interaction.getItemSeq(), e.getMessage());
            }
        }
    }
}
