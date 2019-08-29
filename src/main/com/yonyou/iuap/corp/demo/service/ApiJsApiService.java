package com.yonyou.iuap.corp.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.corp.demo.utils.HttpClientUtil;
import com.yonyou.iuap.corp.demo.utils.MD5SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述: jsapi鉴权
 *
 * @author mantan
 * @create 2019-04-09 下午12:52
 */
@Service
public class ApiJsApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiJsApiService.class);

    private final static String URL_GET_JSTICKLET = "/open/diwork/jsbridge/jsticket";

    @Value("${api.host}")
    private String apiHost;

    @Value("${app.code}")
    private String agentId;


    /**
     * 获取JsTicket
     * @param accessToken
     * @return
     * @throws Exception
     */
    public String getJsTicket(String accessToken) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        String result = HttpClientUtil.get(apiHost + URL_GET_JSTICKLET,params);

        logger.info("getJsTicket result:{}", result);
        return JSONObject.parseObject(result).getJSONObject("data").getString("js_ticket");
    }

    /**
     * 生成签名
     * @param agentId
     * @param timeStamp
     * @param jsTicket
     * @return
     * @throws Exception
     */
    private String getSignature(String agentId, String timeStamp, String jsTicket) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("timeStamp", timeStamp);
        params.put("jsTicket", jsTicket);
        return MD5SignUtil.buildAscSign(params);
    }

    /**
     * 生成鉴权信息
     * @param accessToken
     * @return
     * @throws Exception
     */
    public Map<String, Object>  getAgentInfo(String accessToken) throws Exception {
        String timeStamp = Long.toString(System.currentTimeMillis());
        String jsTicket = getJsTicket(accessToken);
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("timeStamp", timeStamp);
        params.put("signature", getSignature(agentId, timeStamp, jsTicket));
        System.out.println(params.toString());
        return params;
    }

}
