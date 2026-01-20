package com.nocdu.druginfo.test;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
    public static void main(String[] args) throws IOException {
        // 테스트할 gnlNmCd (로그에서 확인된 값)
        String gnlNmCd = "101804AGN"; 
        
        // 서비스키 (사용자 입력 필요)
        String serviceKey = "SERVICE_KEY_HERE"; 
        
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/msupCmpnMeftInfoService/getMajorCmpnNmCdList");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); 
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("gnlNmCd","UTF-8") + "=" + URLEncoder.encode(gnlNmCd, "UTF-8"));
        
        // type 파라미터 제거 (기본 XML) 또는 xml 명시
        // urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        System.out.println("Request URL: " + url.toString());
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        
        System.out.println("Response code: " + conn.getResponseCode());
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }
        
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        System.out.println("=== Response Body ===");
        System.out.println(sb.toString());
    }
}