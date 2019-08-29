package com.yonyou.iuap.corp.demo.service;

import com.yonyou.iuap.corp.demo.model.UserIdsParam;
import com.yonyou.iuap.corp.demo.model.UserPageParam;
import com.yonyou.iuap.corp.demo.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 用户接口
 */
@Service
public class ApiUserService {

    private static final Logger logger = LoggerFactory.getLogger(ApiUserService.class);

    private final static String URL_USER_PAGE_LIST = "/open/diwork/users/user_page_list";

    private final static String URL_SEARCH_USER = "/open/diwork/users/search_page_list";

    private final static String URL_LIST_BY_IDS = "/open/diwork/users/list_by_ids";


    @Value("${api.host}")
    private String apiHost;

    /**
     * 分页查询当前租户下所有用户
     *
     * @param pageParam
     * @param accessToken
     * @return
     * @throws Exception
     */
    public String getUserPageList(UserPageParam pageParam, String accessToken) throws Exception {
        String requestUrl = apiHost + URL_USER_PAGE_LIST + "?access_token=" + accessToken;
        String result = HttpClientUtil.jsonPost(requestUrl, pageParam);
        logger.info("getUserPageList result:{}", result);
        return result;
    }

    /**
     * 根据手机号/邮箱/用户名分页查询当前租户下用户
     *
     * @param pageParam
     * @param accessToken
     * @return
     * @throws Exception
     */
    public String searchUser(UserPageParam pageParam, String accessToken) throws Exception {
        String requestUrl = apiHost + URL_SEARCH_USER + "?access_token=" + accessToken;
        String result = HttpClientUtil.jsonPost(requestUrl, pageParam);
        logger.info("searchUser result:{}", result);
        return result;
    }

    /**
     * 根据userids查询用户信息
     *
     * @param userIdsParam
     * @param accessToken
     * @return
     * @throws Exception
     */
    public String listByIds(UserIdsParam userIdsParam, String accessToken) throws Exception {
        String requestUrl = apiHost + URL_LIST_BY_IDS + "?access_token=" + accessToken;
        String result = HttpClientUtil.jsonPost(requestUrl, userIdsParam);
        logger.info("listByIds result:{}", result);
        return result;
    }
}
