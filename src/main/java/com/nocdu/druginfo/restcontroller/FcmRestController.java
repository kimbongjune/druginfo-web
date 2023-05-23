package com.nocdu.druginfo.restcontroller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.nocdu.druginfo.fcm.request.FcmRequestDto;
import com.nocdu.druginfo.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 2023-05-23
 * @author 김봉준
 * FCM API 컨트롤러
 * 의약품 최소 보유량 도달시 동작한다.
 */
@RestController
@RequestMapping("/DrugInfo/fcm")
@RequiredArgsConstructor
public class FcmRestController {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(FcmRestController.class);

    //FCM 서비스 객체
    private final FcmService fcmService;

    /**
     * @since 2023-05-23
     * @author 김봉준
     * @param fcmRequestDto FcmRequestDto
     * @throws FirebaseMessagingException
     */
    @GetMapping(value = "/send")
    public String sendFcm(FcmRequestDto fcmRequestDto) throws FirebaseMessagingException {
        Logger.info("fcm 전송 로직 수행");
        fcmService.sendFcm(fcmRequestDto.getToken(), fcmRequestDto.getTitle(), fcmRequestDto.getMessage());
        return "";
    }
}
