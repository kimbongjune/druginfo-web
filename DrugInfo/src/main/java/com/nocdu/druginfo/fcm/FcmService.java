package com.nocdu.druginfo.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;

/**
 * 	@author 김봉준
 * 	FCM 전송 인터페이스 클래스
 */
public interface FcmService {
	void sendFcm(String token, String title, String body)throws FirebaseMessagingException ;
}
