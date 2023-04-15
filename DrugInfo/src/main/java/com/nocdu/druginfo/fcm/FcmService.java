package com.nocdu.druginfo.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;

public interface FcmService {
	void sendFcm(String token, String title, String body)throws FirebaseMessagingException ;
}
