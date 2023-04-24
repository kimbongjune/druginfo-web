package com.nocdu.druginfo.fcm;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.nocdu.druginfo.EgovMessageSource;

/**
 * 	@author 김봉준
 * 	FCM 전송 구현 클래스
 */
@Service(value="FcmSendService")
public class FcmServiceImpl implements FcmService {
	
	/** EgovMessageSource **/
	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;
	
	/**
	 * @author 김봉준
	 * @param token
	 * @param title
	 * @param body
	 * @throws FirebaseMessagingException
	 */
	@Override
	public void sendFcm(String token, String title, String body) throws FirebaseMessagingException {
		//노티피케이션을 세팅한다.
		Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600*1000)
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setRestrictedPackageName(egovMessageSource.getMessage("fcm.package.name"))
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

        // Send a message to the device corresponding to the provided registration token.
        String response = FirebaseMessaging.getInstance().send(message);
	}
}
