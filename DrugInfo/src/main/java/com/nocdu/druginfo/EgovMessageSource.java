package com.nocdu.druginfo;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 	@author 김봉준
 *	전자정부 프레임워크의 EgovMessageSource 클래스를 그대로 구현하였음
 *	properties에 접근하여 데이터를 사용하기 위함
 */
public class EgovMessageSource extends ReloadableResourceBundleMessageSource implements MessageSource {

    private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

    /**
     * getReloadableResourceBundleMessageSource() 
     * @param reloadableResourceBundleMessageSource - resource MessageSource
     * @return ReloadableResourceBundleMessageSource
     */    
    public void setReloadableResourceBundleMessageSource(ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource) {
        this.reloadableResourceBundleMessageSource = reloadableResourceBundleMessageSource;
    }
    
    /**
     * getReloadableResourceBundleMessageSource() 
     * @return ReloadableResourceBundleMessageSource
     */    
    public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
        return reloadableResourceBundleMessageSource;
    }
    
    /**
     * 정의된 메세지 조회
     * @param code - 메세지 코드
     * @return String
     */    
    public String getMessage(String code) {
        return getReloadableResourceBundleMessageSource().getMessage(code, null, Locale.getDefault());
    }

}