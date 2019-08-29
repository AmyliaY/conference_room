package com.yonyou.iuap.corp.demo.utils;

import org.springframework.util.CollectionUtils;

import java.security.MessageDigest;
import java.util.*;

/**
 * MD5加签
 */
public class MD5SignUtil {

    /***
     * MD5加密 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 生成签名
     *
     * @param map
     * @return
     */
    public static String buildAscSign(Map<String, Object> map) {
        Map<String, Object> sortMap = new TreeMap<>();
        if (!CollectionUtils.isEmpty(map)) {
            sortMap.putAll(sortMap);
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (!CollectionUtils.isEmpty(map)) {
            //排序
            Collection<String> key = map.keySet();
            List<String> list = new ArrayList<>(key);
            Collections.sort(list);
            for (int i = 0; i < list.size(); i++) {
                stringBuffer.append(list.get(i)).append("=").append(map.get(list.get(i)));
                stringBuffer.append("&");
            }
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        //加密
        return string2MD5(stringBuffer.toString());
    }

    public static void main(String[] args) {
        Map<String, Object> jsonParam = new HashMap<>();
        jsonParam.put("agentId", "355afeb4-55e4-4104-8df5-60d13da44a43");
        jsonParam.put("timeStamp", "1557886784273");
        jsonParam.put("jsTicket", "d3b1990d-edc8-47e7-9c16-5998f795a7f3");
        String sign = MD5SignUtil.buildAscSign(jsonParam);
        System.out.println(sign);
    }
}
