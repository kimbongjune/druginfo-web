package com.nocdu.druginfo.fcm.service;

import com.google.firebase.messaging.FirebaseMessagingException;

/**
 * @since 2023-05-23
 * @author 김봉준
 * FCM 전송 서비스 인터페이스 클래스
 */
public interface FcmService {

    /**
     * @author 김봉준
     * @param token
     * @param title
     * @param body
     * @throws FirebaseMessagingException
     */
    void sendFcm(String token, String title, String body)throws FirebaseMessagingException;
}
