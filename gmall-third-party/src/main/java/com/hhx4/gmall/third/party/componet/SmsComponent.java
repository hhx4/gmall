package com.hhx4.gmall.third.party.componet;

import com.sun.deploy.net.HttpUtils;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @created by wt at 2021-11-25 21:16
 **/
public class SmsComponent {
    private String host;
    private String path;
    private String skin;
    private String sign;
    private String appcode;

    public void sendCode(String phone,String code) {
        String method = "GET";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE" + appcode);
        Map<String,String> queries = new HashMap<>();
        queries.put("code",code);
        queries.put("phone",phone);
        queries.put("skin",skin);
        queries.put("sign",sign);

        //TODO: add
    }
}