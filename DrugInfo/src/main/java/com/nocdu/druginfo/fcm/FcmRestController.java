package com.nocdu.druginfo.fcm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.firebase.messaging.FirebaseMessagingException;

@Controller
@RequestMapping("/fcm")
public class FcmRestController {
	
	@Resource(name="FcmSendService")
	FcmService fcmService;

	@RequestMapping("/send")
	@ResponseBody
	public String sendFcm(HttpServletRequest request, @RequestParam String token, @RequestParam String title, @RequestParam String message) throws FirebaseMessagingException {
		System.out.println("fcm 전송 로직 수행");
		fcmService.sendFcm(token, title, message);
		return "";
	}
}
