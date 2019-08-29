package com.yonyou.iuap.corp.demo.service;

import com.yonyou.iuap.corp.demo.model.NotifyShareContent;
import com.yonyou.iuap.corp.demo.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 描述: 消息
 *
 * @author mantan
 * @create 2019-04-09 下午12:52
 */
@Service
public class ApiMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ApiMessageService.class);

    private final static String URL_NOTIFY_SHARE = "/open/diwork/notify/share";

    @Value("${api.host}")
    private String apiHost;

    /**
     * 工作通知
     *
     * @return
     */
    public String sendNotifyShare(NotifyShareContent notifyShareContent, String accessToken) throws Exception {
        String requestUrl = apiHost + URL_NOTIFY_SHARE + "?access_token=" + accessToken;
        String result = HttpClientUtil.jsonPost(requestUrl, notifyShareContent);
        logger.info("sendNotifyShare result:{}", result);
        return result;
    }
}
