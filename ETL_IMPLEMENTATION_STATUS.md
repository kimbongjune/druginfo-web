# 의약품 정보 API 서버 - 구현 현황 및 다음 에이전트 TODO

> **마지막 업데이트**: 2026-01-18

---

## ✅ 완료된 작업

### 1. 프로젝트 설정
- [x] Spring Boot 3.2.5 + Java 21 업그레이드
- [x] QueryDSL 5.0 + Spring Batch 의존성 추가
- [x] 로컬 MySQL 연결 (`druginfo` DB, root/qwer123#)
- [x] 클린 아키텍처 패키지 구조 적용

### 2. 공공데이터 API 연동
- [x] `OpenApiClient.java` - WebClient 기반 API 호출
- [x] 8개 API 응답 DTO 구현
- [x] 타임아웃/버퍼 설정 (`WebClientConfig.java`)

### 3. 도메인 엔티티
- [x] `Drug` - 의약품 기본정보
- [x] `DrugIdentification` - 낱알식별정보
- [x] `DrugInteraction` - 병용금기 정보
- [x] `DrugDurWarning` - DUR 경고 (연령/임부/노인)
- [x] `DrugPrice` - 약가 정보
- [x] `DrugIngredient` - 성분약효 정보
- [x] `DrugSafetyInfo` - 안전정보 (미사용)

### 4. Spring Batch ETL (8개 Step)
- [x] `DrugEtlBatchConfig.java` - 8개 Step Job 설정
- [x] 8개 Reader (페이징 처리)
- [x] 8개 Processor (DTO → Entity 변환)
- [x] 6개 Writer (Upsert 로직)

### 5. 스케줄러
- [x] `DrugEtlScheduler.java` - 매주 화요일 새벽 3시 자동 실행
- [x] `application.yml` 설정: `etl.scheduler.cron: "0 0 3 * * TUE"`

### 6. 검색 API
- [x] `DrugSearchController.java` - 통합검색, 낱알식별검색
- [x] `DrugSearchService.java`
- [x] `DrugRepositoryImpl.java` - QueryDSL 동적 쿼리
- [x] `PageResponse.java`, `DrugSearchResponse.java`

### 7. 기타
- [x] `GlobalExceptionHandler.java` - 전역 예외 처리
- [x] `BatchController.java` - ETL 수동 실행 API
- [x] Swagger UI 설정 완료

### 8. 빌드
- [x] `gradlew.bat clean build -x test` 성공
- [x] 애플리케이션 정상 시작 확인

---

## ❌ 미완료 - 다음 에이전트가 해야 할 것

### 1. ETL 실제 실행 및 데이터 검증 (필수)
```bash
# 앱 시작
set "JAVA_HOME=c:\java\jdk-21.0.2" && gradlew.bat bootRun

# ETL 실행
curl -X POST "http://localhost:8080/DrugInfo/api/v1/batch/drug-etl"

# 데이터 확인 (MySQL)
SELECT COUNT(*) FROM drug;               -- e약은요 (약 4,742건 예상)
SELECT COUNT(*) FROM drug_identification; -- 낱알식별 (약 80,000건 예상)
SELECT COUNT(*) FROM drug_interaction;    -- 병용금기
SELECT COUNT(*) FROM drug_dur_warning;    -- 연령/임부/노인 금기
SELECT COUNT(*) FROM drug_price;          -- 약가
SELECT COUNT(*) FROM drug_ingredient;     -- 성분정보
```

### 2. HIRA API 인증키 확인
- Step 7(약가정보), Step 8(성분정보)은 **건강보험심사평가원** API
- 현재 설정된 인증키가 HIRA API에 대해서도 유효한지 확인 필요
- 에러 발생 시 별도 인증키 신청 필요할 수 있음

### 3. 테스트 코드 작성
- [ ] 단위 테스트 (Service, Repository)
- [ ] 통합 테스트 (Controller)
- [ ] ETL 배치 테스트
- [ ] API 클라이언트 Mock 테스트

### 4. 검색 API 고도화
- 현재: 기본 검색만 지원
- ✅ 추가 완료:
  - DUR 경고 정보 검색 결과에 포함
  - 약가 정보 검색 결과에 포함
  - 성분/약효 정보 검색 결과에 포함 (성분정보 ETL 완료 후)

### 5. README 업데이트
- ✅ 프로젝트 설명
- ✅ 설치/실행 방법
- ✅ API 명세


### 6. 해결
- [x] ETL문제 다 해결하면 ETL에서 잠깐 주석한부분 해제(e약은요 등)
- [x] 의약품안전정보 서비스 api호출하게 (DUR 데이터 활용으로 대체, SafetyInfo 도메인 미사용)
- [ ] drug_ingredient 테이블 안채워짐

---

## 📁 파일 위치 참조

```
src/main/java/com/nocdu/druginfo/
├── batch/
│   ├── DrugEtlBatchConfig.java      # 메인 Job 설정
│   ├── DrugEtlScheduler.java        # 스케줄러
│   ├── reader/                      # 8개
│   ├── processor/                   # 8개
│   └── writer/                      # 6개
├── domain/drug/
│   ├── Drug.java, DrugIdentification.java, ...
│   └── repository/                  # 6개
├── infrastructure/api/
│   ├── OpenApiClient.java
│   └── dto/                         # 8개 API 응답 DTO
├── api/
│   ├── drug/DrugSearchController.java
│   └── batch/BatchController.java
└── application/drug/DrugSearchService.java

src/main/resources/
└── application.yml                  # 모든 설정
```

---

## ⚙️ 주요 설정 요약

| 항목 | 값 |
|------|-----|
| 포트 | `8080` |
| Context Path | `/DrugInfo` |
| DB | `mysql://localhost:3306/druginfo` |
| DB 계정 | `root` / `qwer123#` |
| ETL 스케줄 | 매주 화요일 03:00 |
| Swagger | `http://localhost:8080/DrugInfo/swagger-ui.html` |
| ETL 수동실행 | `POST /DrugInfo/api/v1/batch/drug-etl` |
