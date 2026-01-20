-- 모든 엔티티 컬럼 사이즈 수정을 위한 DDL

-- [1] 기존 인덱스 삭제
-- TEXT로 변경할 컬럼이 포함된 인덱스는 삭제해야 변경 가능합니다.
ALTER TABLE drug DROP INDEX idx_drug_item_name;
ALTER TABLE drug DROP INDEX idx_drug_entp_name;
ALTER TABLE drug_identification DROP INDEX idx_ident_print_front;

-- [2] 컬럼 타입 변경 (VARCHAR -> TEXT / 길이 확장)
-- 데이터 잘림 방지를 위해 충분한 크기로 변경

-- Drug 테이블
ALTER TABLE drug MODIFY COLUMN item_seq VARCHAR(50);
ALTER TABLE drug MODIFY COLUMN item_name TEXT;
ALTER TABLE drug MODIFY COLUMN entp_name VARCHAR(500);
ALTER TABLE drug MODIFY COLUMN storage_method TEXT;
ALTER TABLE drug MODIFY COLUMN image_url TEXT;

-- DrugIdentification 테이블
ALTER TABLE drug_identification MODIFY COLUMN item_seq VARCHAR(50);
ALTER TABLE drug_identification MODIFY COLUMN item_name TEXT;
ALTER TABLE drug_identification MODIFY COLUMN entp_name VARCHAR(500);
ALTER TABLE drug_identification MODIFY COLUMN drug_shape VARCHAR(100);
ALTER TABLE drug_identification MODIFY COLUMN color_front VARCHAR(100);
ALTER TABLE drug_identification MODIFY COLUMN color_back VARCHAR(100);
ALTER TABLE drug_identification MODIFY COLUMN print_front TEXT;
ALTER TABLE drug_identification MODIFY COLUMN print_back TEXT;
ALTER TABLE drug_identification MODIFY COLUMN line_front VARCHAR(100);
ALTER TABLE drug_identification MODIFY COLUMN line_back VARCHAR(100);
ALTER TABLE drug_identification MODIFY COLUMN image_url TEXT;
ALTER TABLE drug_identification MODIFY COLUMN form_code_name VARCHAR(200);
ALTER TABLE drug_identification MODIFY COLUMN class_no VARCHAR(50);
ALTER TABLE drug_identification MODIFY COLUMN class_name TEXT;
ALTER TABLE drug_identification MODIFY COLUMN etc_otc_name VARCHAR(100);
ALTER TABLE drug_identification MODIFY COLUMN mark_code_front TEXT;
ALTER TABLE drug_identification MODIFY COLUMN mark_code_back TEXT;

-- DrugInteraction 테이블
ALTER TABLE drug_interaction MODIFY COLUMN item_seq VARCHAR(50);
ALTER TABLE drug_interaction MODIFY COLUMN item_name TEXT;
ALTER TABLE drug_interaction MODIFY COLUMN entp_name VARCHAR(500);
ALTER TABLE drug_interaction MODIFY COLUMN main_ingr_code TEXT;
ALTER TABLE drug_interaction MODIFY COLUMN main_ingr_kor TEXT;
ALTER TABLE drug_interaction MODIFY COLUMN mixture_item_seq VARCHAR(50);
ALTER TABLE drug_interaction MODIFY COLUMN mixture_item_name TEXT;
ALTER TABLE drug_interaction MODIFY COLUMN mixture_entp_name VARCHAR(500);
ALTER TABLE drug_interaction MODIFY COLUMN mixture_main_ingr_code TEXT;
ALTER TABLE drug_interaction MODIFY COLUMN mixture_main_ingr TEXT;
ALTER TABLE drug_interaction MODIFY COLUMN type_code VARCHAR(50);
ALTER TABLE drug_interaction MODIFY COLUMN type_name VARCHAR(200);

-- DrugDurWarning 테이블
ALTER TABLE drug_dur_warning MODIFY COLUMN item_seq VARCHAR(50);
ALTER TABLE drug_dur_warning MODIFY COLUMN item_name TEXT;
ALTER TABLE drug_dur_warning MODIFY COLUMN entp_name VARCHAR(500);
ALTER TABLE drug_dur_warning MODIFY COLUMN main_ingr_code TEXT;
ALTER TABLE drug_dur_warning MODIFY COLUMN main_ingr_kor TEXT;
ALTER TABLE drug_dur_warning MODIFY COLUMN type_code VARCHAR(50);
ALTER TABLE drug_dur_warning MODIFY COLUMN type_name VARCHAR(200);

-- DrugPrice 테이블
ALTER TABLE drug_price MODIFY COLUMN item_seq VARCHAR(50);
ALTER TABLE drug_price MODIFY COLUMN item_name TEXT;
ALTER TABLE drug_price MODIFY COLUMN entp_name VARCHAR(500);
ALTER TABLE drug_price MODIFY COLUMN edi_code VARCHAR(50);
ALTER TABLE drug_price MODIFY COLUMN unit VARCHAR(100);
ALTER TABLE drug_price MODIFY COLUMN spec TEXT;
ALTER TABLE drug_price MODIFY COLUMN apply_start_date VARCHAR(20);
ALTER TABLE drug_price MODIFY COLUMN apply_end_date VARCHAR(20);
ALTER TABLE drug_price MODIFY COLUMN pay_type VARCHAR(50);
ALTER TABLE drug_price MODIFY COLUMN gnl_nm_cd VARCHAR(50);

-- DrugIngredient 테이블
ALTER TABLE drug_ingredient MODIFY COLUMN component_code VARCHAR(50);
ALTER TABLE drug_ingredient MODIFY COLUMN component_kor_name TEXT;
ALTER TABLE drug_ingredient MODIFY COLUMN component_eng_name TEXT;
ALTER TABLE drug_ingredient MODIFY COLUMN formula_code VARCHAR(50);
ALTER TABLE drug_ingredient MODIFY COLUMN formula_name TEXT;
ALTER TABLE drug_ingredient MODIFY COLUMN adm_route VARCHAR(200);
ALTER TABLE drug_ingredient MODIFY COLUMN atc_code VARCHAR(50);
ALTER TABLE drug_ingredient MODIFY COLUMN atc_name TEXT;

-- DrugSafetyInfo 테이블
ALTER TABLE drug_safety_info MODIFY COLUMN item_seq VARCHAR(50);
ALTER TABLE drug_safety_info MODIFY COLUMN storage_method TEXT;
ALTER TABLE drug_safety_info MODIFY COLUMN valid_term VARCHAR(500);


-- [3] 인덱스 재생성 (Prefix Index 사용)
-- TEXT 컬럼은 전체 인덱싱이 불가능하므로, 앞부분(Prefix)만 인덱싱합니다.
-- 예: item_name(255) -> 앞 255자만 인덱스에 포함하여 검색 성능 유지

CREATE INDEX idx_drug_item_name ON drug (item_name(255));
CREATE INDEX idx_drug_entp_name ON drug (entp_name(255));
-- print_front는 식별문구라 검색에 중요할 수 있으므로 prefix 인덱스 추가
CREATE INDEX idx_ident_print_front ON drug_identification (print_front(255));
