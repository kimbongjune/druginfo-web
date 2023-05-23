package com.nocdu.druginfo.fcm.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @since 2023-05-23
 * @author 김봉준
 * FCM 요청 객체
 */

@Data
@Getter
@Setter
public class FcmRequestDto {
    private String token;
    private String title;
    private String message;
}
