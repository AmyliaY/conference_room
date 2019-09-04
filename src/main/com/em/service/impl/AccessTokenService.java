package main.com.em.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import main.com.em.crypto.SignHelper;
import main.com.em.utils.HttpClientUtil;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 自建应用获取access_token

 */
@Service
public class AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);

    private final static String URL_TOKEN = "/open-auth/selfAppAuth/getAccessToken";

    private final static String openAuthHost = "http://open.yonyoucloud.com";

    private final static String appKey = "a6288fdb8f1d4d72a861ca7800dd2822";

    private final static String appSecret = "b1174e6231f14fc7ae567bbd73ab06b8";

    /**
     * 获取自建应用access_token
     */
    public String getAccessToken() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        // 除签名外的其他参数
        params.put("appKey", appKey);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
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
        return data.toJSONString();
    }
}
