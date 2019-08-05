package com.qjq.guava;

import com.qjq.guava.helper.XYUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignMain {
    public static void main(String[] args) {
        String appKey="06f3775e37d04533ac2c058b16bfdfdb";
        String app="a82f153b4c3e4689babf2ddf6bb53801";
        String dateTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String method="/callbackDeliveryOrder";
        Map<String,String> map=new HashMap<>();
        map.put("orderId","4040034753870293906");
        Map<String,String> data= XYUtils.packageParams(appKey,app,dateTime,method,map);
        System.out.println(data.get("sign"));
        System.out.println(data.get("timestamp"));
        System.out.println(data.get("orderId"));
//        System.out.println(Boolean.toString(true));
    }

}
