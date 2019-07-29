package com.qjq.guava.helper;

import java.security.MessageDigest;

public class ParseMD5 {

    /**
     * 32位小写MD5
     *
     * @param str
     * @return
     */
    public static String parseStrToMd5L32(String str) {

        if (null == str) {
            return str;
        }

        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b: bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(bt));
            }

            reStr = sb.toString();

            return reStr;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 32位大写MD5
     *
     * @param str
     * @return
     */
    public static String parseStrToMd5U32(String str) {
        String reStr = parseStrToMd5L32(str);
        if (null != reStr) {
            reStr = reStr.toUpperCase();
        }
        return reStr;
    }
}
