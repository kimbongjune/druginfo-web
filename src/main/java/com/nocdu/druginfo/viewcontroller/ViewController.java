package com.nocdu.druginfo.viewcontroller;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @since 2023-05-24
 * @author 김봉준
 * view 컨트롤러
 */
@Controller
@RequestMapping("/")
public class ViewController {

    /**
     * @since 2023-05-24
     * @author 김봉준
     * 개인정보 처리방침 페이지 컨트롤러 메서드
     */
    @RequestMapping("/agreements")
    public String hello(){
        return "agreements";
    }
}
