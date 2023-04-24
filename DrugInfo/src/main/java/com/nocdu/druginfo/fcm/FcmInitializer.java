package com.nocdu.druginfo.fcm;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.nocdu.druginfo.EgovMessageSource;

/**
 * 	@author 김봉준
 *	서버에서 FCM을 전송하기 위해 FCM 설정을 활성화 한다.
 *	서버에 있는 json 파일을 읽어 FCM을 설정한다.
 */
@Configuration
public class FcmInitializer {
	
	/** EgovMessageSource **/
	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;
	
	//스프링 빈을 등록 할 때 최초 한번 호출하며 FCM 설정을 한다.
	@PostConstruct
    public void init() {
		try {
			//System.out.println("@@@@@@@@@@@@@@@ 프로젝트 아이디" + egovMessageSource.getMessage("fcm.project.id"));
			//서버 ClassPath에 위치한 Json 파일을 가져온다.
			ClassPathResource resource = new ClassPathResource("druginfo-fcm-key.json");
//			FileInputStream serviceAccount = new FileInputStream(resource.getPath());
//			System.out.println("@@@@@@@@@@@@@@@ json Path" + resource.getPath());

			//FCM 설정
			FirebaseOptions options = new FirebaseOptions.Builder()
				//읽어 온 Json 파일을 stream 형태로 변경 후 계정정보를 세팅한다.
				.setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
				//FCM 프로젝트 아이디를 properties에서 읽어 세팅한다.
				.setProjectId(egovMessageSource.getMessage("fcm.project.id"))
				.build();
			//세팅한 설정 정보를 적용한다.
			FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
