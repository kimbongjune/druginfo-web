package com.nocdu.druginfo.batch;

import com.nocdu.druginfo.batch.processor.*;
import com.nocdu.druginfo.batch.reader.*;
import com.nocdu.druginfo.batch.writer.*;
import com.nocdu.druginfo.domain.drug.*;
import com.nocdu.druginfo.infrastructure.api.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 의약품 데이터 ETL 배치 Job 설정
 * 8개 Step으로 모든 API 데이터 수집:
 * 1. e약은요 (의약품 개요정보)
 * 2. 낱알식별정보
 * 3. DUR 병용금기
 * 4. DUR 특정연령대금기
 * 5. DUR 임부금기
 * 6. DUR 노인주의
 * 7. 약가정보 (HIRA)
 * 8. 성분약효정보 (HIRA)
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DrugEtlBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    
    // Step 1: e약은요
    private final DrugEasyInfoReader drugEasyInfoReader;
    private final DrugProcessor drugProcessor;
    private final DrugWriter drugWriter;
    
    // Step 2: 낱알식별정보
    private final DrugIdentificationReader drugIdentificationReader;
    private final DrugIdentificationProcessor drugIdentificationProcessor;
    private final DrugIdentificationWriter drugIdentificationWriter;
    
    // Step 3: DUR 병용금기
    private final DurCombinedTabooReader durCombinedTabooReader;
    private final DurCombinedTabooProcessor durCombinedTabooProcessor;
    private final DrugInteractionWriter drugInteractionWriter;
    
    // Step 4-6: DUR 연령/임부/노인 (공통 Writer 사용)
    private final DurAgeTabooReader durAgeTabooReader;
    private final DurAgeTabooProcessor durAgeTabooProcessor;
    private final DurPregnantTabooReader durPregnantTabooReader;
    private final DurPregnantTabooProcessor durPregnantTabooProcessor;
    private final DurElderlyReader durElderlyReader;
    private final DurElderlyProcessor durElderlyProcessor;
    private final DrugDurWarningWriter drugDurWarningWriter;
    
    // Step 7: 약가정보
    private final DrugPriceReader drugPriceReader;
    private final DrugPriceProcessor drugPriceProcessor;
    private final DrugPriceWriter drugPriceWriter;
    
    // Step 8: 성분약효정보
    private final DrugIngredientReader drugIngredientReader;
    private final DrugIngredientProcessor drugIngredientProcessor;
    private final DrugIngredientWriter drugIngredientWriter;

    private static final int CHUNK_SIZE = 100;

    /**
     * 의약품 ETL 메인 Job
     * 1. e약은요, 낱알식별, DUR 등은 이미 완료되었으므로 주석 처리
     * 2. 약가 정보부터 시작
     * 3. SafetyInfo 업데이트 Step(Tasklet)은 불필요하므로 제거 (Service에서 처리함)
     */
    @Bean
    public Job drugEtlJob() {
        return new JobBuilder("drugEtlJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fetchDrugEasyInfoStep())  // 완료
                .next(fetchDrugIdentificationStep()) // 완료
                .next(fetchDurCombinedTabooStep()) // 완료
                .next(fetchDurAgeTabooStep())      // 완료
                .next(fetchDurPregnantTabooStep()) // 완료
                .next(fetchDurElderlyStep())       // Step 6
                .next(fetchDrugPriceStep())        // Step 7: 약가정보
                .next(fetchDrugIngredientStep())  // Step 8: 성분약효정보
                .build();
    }

    /**
     * Step 1: 의약품개요정보(e약은요) 수집
     */
    @Bean
    public Step fetchDrugEasyInfoStep() {
        return new StepBuilder("fetchDrugEasyInfoStep", jobRepository)
                .<DrugEasyInfoApiResponse.Item, Drug>chunk(CHUNK_SIZE, transactionManager)
                .reader(drugEasyInfoReader)
                .processor(drugProcessor)
                .writer(drugWriter)
                .faultTolerant()
                .skipLimit(100)
                .skip(Exception.class)
                .build();
    }

    /**
     * Step 2: 낱알식별정보 수집
     */
    @Bean
    public Step fetchDrugIdentificationStep() {
        return new StepBuilder("fetchDrugIdentificationStep", jobRepository)
                .<DrugIdentificationApiResponse.Item, DrugIdentification>chunk(CHUNK_SIZE, transactionManager)
                .reader(drugIdentificationReader)
                .processor(drugIdentificationProcessor)
                .writer(drugIdentificationWriter)
                .faultTolerant()
                .skipLimit(100)
                .skip(Exception.class)
                .build();
    }

    /**
     * Step 3: DUR 병용금기 수집
     */
    @Bean
    public Step fetchDurCombinedTabooStep() {
        return new StepBuilder("fetchDurCombinedTabooStep", jobRepository)
                .<DurCombinedTabooApiResponse.Item, DrugInteraction>chunk(CHUNK_SIZE, transactionManager)
                .reader(durCombinedTabooReader)
                .processor(durCombinedTabooProcessor)
                .writer(drugInteractionWriter)
                .faultTolerant()
                .skipLimit(100)
                .skip(Exception.class)
                .build();
    }

    /**
     * Step 4: DUR 특정연령대금기 수집
     */
    @Bean
    public Step fetchDurAgeTabooStep() {
        return new StepBuilder("fetchDurAgeTabooStep", jobRepository)
                .<DurAgeTabooApiResponse.Item, DrugDurWarning>chunk(CHUNK_SIZE, transactionManager)
                .reader(durAgeTabooReader)
                .processor(durAgeTabooProcessor)
                .writer(drugDurWarningWriter)
                .faultTolerant()
                .skipLimit(100)
                .skip(Exception.class)
                .build();
    }

    /**
     * Step 5: DUR 임부금기 수집
     */
    @Bean
    public Step fetchDurPregnantTabooStep() {
        return new StepBuilder("fetchDurPregnantTabooStep", jobRepository)
                .<DurPregnantTabooApiResponse.Item, DrugDurWarning>chunk(CHUNK_SIZE, transactionManager)
                .reader(durPregnantTabooReader)
                .processor(durPregnantTabooProcessor)
                .writer(drugDurWarningWriter)
                .faultTolerant()
                .skipLimit(100)
                .skip(Exception.class)
                .build();
    }

    /**
     * Step 6: DUR 노인주의 수집
     */
    @Bean
    public Step fetchDurElderlyStep() {
        return new StepBuilder("fetchDurElderlyStep", jobRepository)
                .<DurElderlyApiResponse.Item, DrugDurWarning>chunk(CHUNK_SIZE, transactionManager)
                .reader(durElderlyReader)
                .processor(durElderlyProcessor)
                .writer(drugDurWarningWriter)
                .faultTolerant()
                .skipLimit(100)
                .skip(Exception.class)
                .build();
    }

    /**
     * Step 7: 약가정보 수집 (HIRA)
     */
    @Bean
    public Step fetchDrugPriceStep() {
        return new StepBuilder("fetchDrugPriceStep", jobRepository)
                .<DrugPriceApiResponse.Item, DrugPrice>chunk(CHUNK_SIZE, transactionManager)
                .reader(drugPriceReader)
                .processor(drugPriceProcessor)
                .writer(drugPriceWriter)
                .faultTolerant()
                .skipLimit(100)
                .skip(Exception.class)
                .build();
    }

    /**
     * Step 8: 성분약효정보 수집 (HIRA)
     */
    @Bean
    public Step fetchDrugIngredientStep() {
        return new StepBuilder("fetchDrugIngredientStep", jobRepository)
                .<DrugIngredientApiResponse.Item, DrugIngredient>chunk(CHUNK_SIZE, transactionManager)
                .reader(drugIngredientReader)
                .processor(drugIngredientProcessor)
                .writer(drugIngredientWriter)
                .faultTolerant()
                .skipLimit(100)
                .skip(Exception.class)
                .build();
    }
}
