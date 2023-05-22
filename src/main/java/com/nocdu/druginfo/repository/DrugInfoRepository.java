package com.nocdu.druginfo.repository;

import com.nocdu.druginfo.Entity.DrugInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @since 2023-05-23
 * @author 김봉준
 * Spring JPA 인터페이스 클래스
 * 스프링 부트로 변경 테스트중
 */
public interface DrugInfoRepository extends JpaRepository<DrugInfoEntity, Long> {
}
