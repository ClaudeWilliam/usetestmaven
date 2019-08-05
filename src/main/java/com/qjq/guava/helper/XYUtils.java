package com.qjq.guava.helper;
////////////////////////////////////////////////////////////////////
//                            _ooOoo_                             //
//                           o8888888o                            //    
//                           88" . "88                            //    
//                           (| -_- |)                            //    
//                           O\  =  /O                            //
//                        ____/`---'\____                         //                        
//                      .'  \\|     |//  `.                       //
//                     /  \\|||  :  |||//  \                      //    
//                    /  _||||| -:- |||||-  \                     //
//                    |   | \\\  -  /// |   |                     //
//                    | \_|  ''\---/''  |   |                     //        
//                    \  .-\__  `-`  ___/-. /                     //        
//                  ___`. .'  /--.--\  `. . ___                   //    
//                ."" '<  `.___\_<|>_/___.'  >'"".                //
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |               //    
//              \  \ `-.   \_ __\ /__ _/   .-` /  /               //
//        ========`-.____`-.___\_____/___.-`____.-'========       //    
//                             `=---='                            //
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^      //
//                         众病悉除，无诸疾苦                       //
////////////////////////////////////////////////////////////////////

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: YangMing-Mude
 * Date: 2019-04-09
 * Time: 11:43 AM
 */
public class XYUtils {


    /**
     * 回调鉴权
     * @param appSecret
     * @param paramsMap
     * @param sign
     * @return
     */
    public static boolean authenticationRequest(String appSecret, Map<String, String> paramsMap, String sign) {


        Map<String, String> sortParamsMap = new TreeMap<String, String>();
        if (paramsMap != null && !paramsMap.isEmpty()) {
            paramsMap.forEach((k, v) -> sortParamsMap.put(k, v));
        }

        StringBuilder sb = new StringBuilder();
        sortParamsMap.forEach((k, v) -> sb.append(k).append("=").append(v));
        //参数值拼接的字符串收尾添加appSecret值
        String waitSignStr = appSecret + sb.toString() + appSecret;

        //获取MD5加密后的字符串
        String compareSign = ParseMD5.parseStrToMd5U32(waitSignStr);

        return StringUtils.equals(sign, compareSign);


    }

    /**
     * 组装参数，包括sign值的生成
     * @param appKey appKey
     * @param appSecret appSecret
     * @param timestamp timestamp
     * @param method method
     * @param paramMap paramMap
     * @return treeMap
     */
    public static TreeMap<String, String> packageParams(String appKey, String appSecret, String timestamp, String method,
                                                        Map<String, String> paramMap) {
        //将请求参数按名称排序
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("appKey", appKey);
        treeMap.put("method", method);
        treeMap.put("timestamp", timestamp);
        if (null != paramMap) {
            treeMap.putAll(paramMap);
        }

        //遍历treeMap，将参数值进行拼接
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = treeMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            sb.append(key).append("=");
            sb.append(treeMap.get(key));
        }

        //参数值拼接的字符串收尾添加appSecret值
        String waitSignStr = appSecret + sb.toString() + appSecret;

        //获取MD5加密后的字符串
        String sign = ParseMD5.parseStrToMd5U32(waitSignStr);

        treeMap.put("sign", sign);

        return treeMap;
    }

}
