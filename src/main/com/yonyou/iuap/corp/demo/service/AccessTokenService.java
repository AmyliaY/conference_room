package com.yonyou.iuap.corp.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.corp.demo.crypto.SignHelper;
import com.yonyou.iuap.corp.demo.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 自建应用获取access_token
 *
 * @author mantan
 * @create 2019-04-09 上午11:52
 */
@Service
public class AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);

    private final static String URL_TOKEN = "/open-auth/selfAppAuth/getAccessToken";

    @Value("${app.key}")
    private String appKey;

    @Value("${app.secret}")
    private String appSecret;

    @Value("${open-auth.host}")
    private String openAuthHost;

    /**
     * 获取自建应用access_token
     * @return
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 除签名外的其他参数
        params.put("appKey", appKey);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));;
        // 计算签名
        String signature = SignHelper.sign(params, appSecret);
        params.put("signature", signature);

        String result =  HttpClientUtil.get(openAuthHost + URL_TOKEN, params);
        // JSONObject
        JSONObject responseData = JSON.parseObject(result);

        // result code
        String code = responseData.getString("code");

        // result message
        String msg = responseData.getString("message");

        // data
        JSONObject data = responseData.getJSONObject("data");
        // access_token & expiresIn(seconds)
        String accessToken = null;
        Integer expiresIn = null;
        if (data != null) {
            accessToken = data.getString("access_token");
            expiresIn = data.getInteger("expire");
        }
        logger.info("result code: " + code);
        logger.info("result message: " + msg);
        logger.info("token: " + accessToken);
        logger.info("expire: " + expiresIn);
        if(accessToken == null){
            throw new RuntimeException("access_token get failed:" + result);
        }
        return accessToken;
    }

    public static void main(String[] args) throws Exception {
        String appKey = "4eb6c47db8aa4778a0add2811fd85302";
        String timestamp = "1547695005873";
        String appSecret = "18650811af30475baaddf44cc2d04ca0";

        Map<String, Object> params = new HashMap<>();
        // 除签名外的其他参数
        params.put("appKey", appKey);
        params.put("timestamp", timestamp);
        // 计算签名
        String signature = SignHelper.sign(params, appSecret);

        logger.info("appKey="+appKey+"&timestamp="+timestamp+"&signature="+signature);

    }
}
