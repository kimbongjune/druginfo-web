package com.nocdu.druginfo.fcm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.firebase.messaging.FirebaseMessagingException;

/**
 * 	@author 김봉준
 *	서버에서 FCM을 전송하는 restCotroller
 *	앱에 저장되어있는 잔여 의약품 미리 알림시 발생한다.
 */
@Controller
@RequestMapping("/fcm")
public class FcmRestController {
	
	/** FCM service **/
	@Resource(name="FcmSendService")
	private FcmService fcmService;

	/**
	 * @author 김봉준
	 * @param request
	 * @param token
	 * @param title
	 * @param message
	 * @return
	 * @throws FirebaseMessagingException
	 */
	@RequestMapping("/send")
	@ResponseBody
	public String sendFcm(HttpServletRequest request, @RequestParam String token, @RequestParam String title, @RequestParam String message) throws FirebaseMessagingException {
		System.out.println("fcm 전송 로직 수행");
		fcmService.sendFcm(token, title, message);
		return "";
	}
}
