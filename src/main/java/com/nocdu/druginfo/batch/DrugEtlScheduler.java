package com.nocdu.druginfo.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 의약품 데이터 ETL 스케줄러
 * - 매주 화요일 새벽 3시에 ETL Job 실행
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrugEtlScheduler {

    private final JobLauncher jobLauncher;
    private final Job drugEtlJob;

    /**
     * 매주 화요일 새벽 3시에 ETL 실행
     * Cron: 초 분 시 일 월 요일
     * 0 0 3 ? * TUE = 매주 화요일 03:00:00
     */
    @Scheduled(cron = "0 0 3 ? * TUE")
    public void runWeeklyEtl() {
        log.info("=== Weekly Drug ETL Job Started (Scheduled) ===");
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("startTime", System.currentTimeMillis())
                    .addString("trigger", "weekly-scheduler")
                    .toJobParameters();

            jobLauncher.run(drugEtlJob, params);
            log.info("=== Weekly Drug ETL Job Completed ===");
        } catch (Exception e) {
            log.error("Weekly Drug ETL Job failed: {}", e.getMessage(), e);
        }
    }
}
