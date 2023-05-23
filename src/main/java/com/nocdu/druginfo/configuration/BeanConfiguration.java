package com.nocdu.druginfo.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @since 2023-05-23
 * @author 김봉준
 * 빈 설정을 위한 config 클래스
 */
@Configuration
public class BeanConfiguration {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(BeanConfiguration.class);

    //FCM 설정을 위한 프로젝트 아이디를 application.properties에서 받아옴
    @Value("${fcm.project.id}")
    private String projectId;

    //FCM 설정을 위한 json 파일 이름을 application.properties에서 받아옴
    @Value("${fcm.setting.file.name}")
    private String fcmSettingFileName;

    //최초 한번만 실행되는 메서드 FCM 발송을 위한 설정을한다.
    @PostConstruct
    public void init() {
        try {
            Logger.info("프로젝트 아이디{}",projectId);
            //서버 ClassPath에 위치한 Json 파일을 가져온다.
            ClassPathResource resource = new ClassPathResource(fcmSettingFileName);
//			FileInputStream serviceAccount = new FileInputStream(resource.getPath());
//			System.out.println("@@@@@@@@@@@@@@@ json Path" + resource.getPath());

            //FCM 설정
            FirebaseOptions options = new FirebaseOptions.Builder()
                    //읽어 온 Json 파일을 stream 형태로 변경 후 계정정보를 세팅한다.
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    //FCM 프로젝트 아이디를 properties에서 읽어 세팅한다.
                    .setProjectId(projectId)
                    .build();
            //세팅한 설정 정보를 적용한다.
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
