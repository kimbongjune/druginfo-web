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

@Configuration
public class FcmInitializer {
	
	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;
	
	@PostConstruct
    public void init() {
		try {
			System.out.println("@@@@@@@@@@@@@@@ 프로젝트 아이디" + egovMessageSource.getMessage("fcm.project.id"));
			ClassPathResource resource = new ClassPathResource("druginfo-fcm-key.json");
//			FileInputStream serviceAccount = new FileInputStream(resource.getPath());
//			System.out.println("@@@@@@@@@@@@@@@ json Path" + resource.getPath());

					FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
					  .setProjectId(egovMessageSource.getMessage("fcm.project.id"))
					  .build();

					FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
