<<<<<<< HEAD
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
=======
# 💊 이약모야
- 의약품을 조회하고 효능과 부작용 등을 조회할 수 있습니다.
- 의약품 복용시간 알람을 등록하고 복용 시간을 알림받을 수 있습니다.
- 잔여 의약품 개수를 등록하고 복용 회차에 따른 재고량 미리 알림을 받을 수 있습니다.
<br><br>

## 📎 목차
  - [프로젝트 개요](#-프로젝트-개요) 
  - [사용 기술](#-사용-기술)
  - [프로젝트 전체 구조](#️-프로젝트-전체-구조)
  - [화면](#-화면)
  - [프로젝트 상세](#-프로젝트-상세)
  - [앱 설치 링크](#-앱-설치-링크)
  - [앱소스 깃허브 링크](#-앱소스-깃허브-링크)
<br><br>

## 📜 프로젝트 개요
- 일반적으로 의약품을 복용하는 사용자들은 약품의 포장을 버린 후에는 해당 의약품에 대한 정보를 쉽게 찾기 어려워집니다. 이러한 문제를 해결하기 위해, 알약의 이름이나 식별 정보만을 입력하면 해당 의약품에 대한 상세 정보와 부작용을 조회할 수 있으면 좋겠다는 아이디어에서 시작되었습니다.<br><br>
- 하나의 알람 그룹에 여러 개의 알람을 등록할 수 있도록 설계되었으며, 각 의약품에 대한 고유 알람을 설정해 사용자에게 알림을 제공합니다. 또한, '보유 의약품 알람 개수 미리 알림' 기능을 통해 사용자가 의약품을 전부 소모하기 전 잔여 의약품 개수를 미리 숙지할 수 있도록 도와줍니다.
<br><br>

## 🛠 사용 기술
- Java 11
- Kotlin 1.7.10
- Spring Boot 2.7.12
- Spring Data JPA 2.7.12
- Gradle 7.5.1
- Coroutine 1.6.1
- Android Jetpack Compose(Room Database 2.4.3, Datastore 1.0.0)
- Retrofit 2.9.0
- AWS EC2(ubuntu 22.04)
- AWS RDS(MySQL 8.0)
- AWS S3
- AWS Code Deploy
- Git & GitHub
- GitHub Actions
- Nginx 1.18.0
<br><br>

## 🗺️ 프로젝트 전체 구조
![test  Copy of Untitled](https://kimbongjune.s3.amazonaws.com/druginfo/document/structure2.png)

<br><br>

## 📱 화면
- Figma를 이용한 화면 디자인
링크 : [Figma](https://www.figma.com/file/aCKYbqfqbG7eoNVFq48Su6/Untitled?type=design&node-id=0%3A1&t=edcxHdPCdZFHOUKU-1)

### 애플리케이션 화면
<div>
  <kbd><img border="1px" width=200, src="https://kimbongjune.s3.amazonaws.com/druginfo/document/search.png">
  <img width="200" src="https://kimbongjune.s3.amazonaws.com/druginfo/document/search_result.png">
  <img width="200" src="https://kimbongjune.s3.amazonaws.com/druginfo/document/search_detail.png">
  <img width="200" src="https://kimbongjune.s3.amazonaws.com/druginfo/document/before_alarm.png">
</div>
  <br><br>
<div>
  <kbd><img width="200" src="https://kimbongjune.s3.amazonaws.com/druginfo/document/add_alarm.png">
  <img width="200" src="https://kimbongjune.s3.amazonaws.com/druginfo/document/after_alarm.png">
</div>
<br><br>
  
## 📝 프로젝트 상세 
[노션에서 보기](https://abounding-clownfish-456.notion.site/cfaf2813854c49c48f21f31ed49201ef)
<br><br>
  
## 💾 앱 설치 링크
[안드로이드 앱 설치 링크](https://play.google.com/store/apps/details?id=com.nocdu.druginformation)
<br><br>
  
## 🔗 앱소스 깃허브 링크
[앱소스 깃허브 링크](https://github.com/kimbongjune/druginfo)
<br><br>
>>>>>>> 991e02688d962c2b22745ccc51b560f1b3ef2750
