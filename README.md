# 이약모야 (DrugInfo) API 서버

> 의약품 정보 통합 검색 및 DUR(Drug Utilization Review) 정보 제공 API 서버

## 📋 프로젝트 개요

- **목적**: 공공데이터포털 API를 통해 의약품 정보를 수집하고, 통합 검색 및 안전사용정보를 제공
- **기술스택**: Spring Boot 3.2.5, Java 21, JPA, QueryDSL, Spring Batch, MySQL
- **데이터 소스**: 식품의약품안전처 + 건강보험심사평가원 공공데이터 API

---

## 🚀 시작하기

### 요구사항
- Java 21+
- MySQL 8.0+
- Gradle 8+

### 설치 및 실행

```bash
# 1. 프로젝트 클론
git clone https://github.com/yourname/druginfo-web.git
cd druginfo-web

# 2. MySQL 데이터베이스 생성
mysql -u root -p -e "CREATE DATABASE druginfo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 3. 빌드
gradlew.bat clean build -x test

# 4. 실행
gradlew.bat bootRun
```

### 환경 설정 (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/druginfo
    username: root
    password: qwer123#

openapi:
  service-key: YOUR_PUBLIC_DATA_PORTAL_API_KEY
```

---

## 📡 API 명세

### 기본 URL
```
http://localhost:8080/DrugInfo/api/v1
```

### Swagger UI
```
http://localhost:8080/DrugInfo/swagger-ui.html
```

---

### 1. 의약품 검색 API

#### 통합 검색
```http
GET /drugs?query={검색어}&page=0&size=15
```

| 파라미터 | 설명 |
|---------|------|
| `query` | 통합 검색어 (이름, 제조사, 효능) |
| `itemName` | 의약품명 |
| `entpName` | 제조사명 |
| `efficacy` | 효능 |
| `className` | 분류명 |
| `etcOtcName` | 전문/일반 (전문의약품, 일반의약품) |

#### 낱알 식별 검색
```http
GET /drugs/identification?shape={모양}&color={색상}&printFront={앞면문구}
```

| 파라미터 | 설명 |
|---------|------|
| `shape` | 모양 (원형, 타원형, 장방형 등) |
| `color` | 색상 (통합 검색) |
| `colorFront` | 앞면 색상 |
| `colorBack` | 뒷면 색상 |
| `printFront` | 앞면 식별문구 |
| `printBack` | 뒷면 식별문구 |
| `formCodeName` | 제형 (정제, 캡슐 등) |

---

### 2. 의약품 상세 조회

#### 상세 정보 (DUR/약가 포함)
```http
GET /drugs/{itemSeq}
```

**응답 예시:**
```json
{
  "itemSeq": "200003738",
  "itemName": "타이레놀정500밀리그램",
  "entpName": "한국얀센",
  "efficacy": "두통, 치통, 발열...",
  "interactions": [
    {
      "mixtureItemName": "와파린정",
      "prohbtContent": "출혈 위험 증가",
      "typeName": "병용금기"
    }
  ],
  "durWarnings": [
    {
      "warningType": "ELDERLY_CAUTION",
      "prohbtContent": "노인 투여 시 주의"
    }
  ],
  "priceInfo": {
    "ediCode": "641900050",
    "drugAmount": 150.00,
    "payType": "급여"
  }
}
```

---

### 3. 개별 정보 조회

#### 병용금기 정보
```http
GET /drugs/{itemSeq}/interactions
```

#### DUR 경고 정보
```http
GET /drugs/{itemSeq}/dur-warnings
```

#### 약가 정보
```http
GET /drugs/{itemSeq}/price
```

---

### 4. ETL 관리

#### ETL 수동 실행
```http
POST /api/v1/batch/drug-etl
```

#### 통계 조회
```http
GET /drugs/stats
```

---

## 🔄 ETL 프로세스

### 수집 데이터

| Step | API | 설명 |
|------|-----|------|
| 1 | e약은요 | 의약품 기본정보 (효능, 복용법) |
| 2 | 낱알식별정보 | 모양, 색상, 식별문구 |
| 3 | 병용금기 | DUR 병용금기 약물 정보 |
| 4 | 연령금기 | 특정 연령대 금기 |
| 5 | 임부금기 | 임산부 금기 |
| 6 | 노인주의 | 노인 주의 정보 |
| 7 | 약가정보 | 보험급여 가격 |
| 8 | 성분정보 | 성분/약효 분류 |

### 스케줄러
- **실행 주기**: 매주 화요일 새벽 3시
- **설정 변경**: `application.yml` > `etl.scheduler.cron`

---

## 📁 프로젝트 구조

```
src/main/java/com/nocdu/druginfo/
├── api/                          # REST Controller
│   ├── drug/DrugSearchController.java
│   └── batch/BatchController.java
├── application/                  # 서비스 레이어
│   └── drug/DrugSearchService.java
├── domain/drug/                  # 도메인 엔티티
│   ├── Drug.java
│   ├── DrugIdentification.java
│   ├── DrugInteraction.java
│   ├── DrugDurWarning.java
│   ├── DrugPrice.java
│   └── repository/
├── batch/                        # Spring Batch ETL
│   ├── DrugEtlBatchConfig.java
│   ├── reader/
│   ├── processor/
│   └── writer/
└── infrastructure/api/           # 외부 API 클라이언트
    ├── OpenApiClient.java
    └── dto/
```

---

## ⚙️ 주요 설정

| 항목 | 값 |
|------|-----|
| 포트 | `8080` |
| Context Path | `/DrugInfo` |
| DB | `mysql://localhost:3306/druginfo` |
| ETL 스케줄 | 매주 화요일 03:00 |

---

## 📜 라이선스

MIT License
