package com.nocdu.druginfo.fcm.service;

import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @since 2023-05-23
 * @author 김봉준
 * FCM 전송 서비스 구현 클래스
 */
@Service
public class FcmServiceImpl implements FcmService{

    //FCM 설정을 위한 애플리케이션의 패키지명을 application.properties에서 받아옴
    @Value("${fcm.package.name}")
    private String packageName;

    /**
     * @author 김봉준
     * @param token
     * @param title
     * @param body
     * @throws FirebaseMessagingException
     */
    @Override
    public void sendFcm(String token, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600*1000)
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setRestrictedPackageName(packageName)
                        .setDirectBootOk(true)
                        .setNotification(AndroidNotification.builder()
                                .setTitle(title) // 알림 제목
                                .setBody(body) // 알림 본문
                                .build())
                        .build())
                .putData("title", title)
                .putData("body", body)
                .setToken(token) // 요청자의 디바이스에 대한 registration token으로 설정
                .build();
        String response = FirebaseMessaging.getInstance().send(message);
    }
}
