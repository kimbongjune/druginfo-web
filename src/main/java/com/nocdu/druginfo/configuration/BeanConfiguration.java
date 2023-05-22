package com.nocdu.druginfo.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 빈 설정을 위한 config 클래스
 */
@Configuration
public class BeanConfiguration {
    
    //application.properties 암호화를 위한 빈 메서드
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor(){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        //암호화에 사용 할 키
        config.setPassword("15-76085808");
        //암호화에 사용 할 알고리즘
        config.setAlgorithm("PBEWithMD5AndDES");
        //반복할 해싱 횟수
        config.setKeyObtentionIterations("1000");
        //내부 암호화 풀의 크기를 설정 크기가 클 수록 더 많은 랜덤데이터가 생성되며 메모리 사용량이 증가된다.
        config.setPoolSize("1");
        //암호화 제공자의 이름 설정 SunJCE는 Sun Microsystems가 제공하는 암호화 제공자이다.
        config.setProviderName("SunJCE");
        //salt키 생성을 수행할 클래스
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        //암호화 후 반환할 문자열의 인코딩 형식
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}
