package com.yonyou.iuap.corp.demo.service;

import com.yonyou.iuap.corp.demo.model.StaffPageParam;
import com.yonyou.iuap.corp.demo.utils.HttpClientConfig;
import com.yonyou.iuap.corp.demo.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工接口
 */
@Service
public class ApiStaffService {

    private static final Logger logger = LoggerFactory.getLogger(ApiUserService.class);

    private final static String URL_STAFF_PAGE_LIST = "/open/diwork/staff/page_list";

    private final static String URL_SEARCH_STAFF= "/open/diwork/staff/info_by_mobile_email";

    @Value("${api.host}")
    private String apiHost;

    /**
     * 分页查询当前租户员工列表
     * @param staffPageParam
     * @param accessToken
     * @return
     * @throws Exception
     */
    public String getStaffPageList(StaffPageParam staffPageParam, String accessToken) throws Exception{
        String requestUrl = apiHost + URL_STAFF_PAGE_LIST + "?access_token=" + accessToken;
        String result = HttpClientUtil.jsonPost(requestUrl,staffPageParam,new HttpClientConfig[0]);
        logger.info("getStaffPageList result:{}", result);
        return result;
    }

    /**
     * 根据手机号或者邮箱查询员工
     * @param accessToken
     * @param type 查询类型：1-手机，2-邮箱
     * @param field 查询值
     * @return
     * @throws Exception
     */
    public String searchStaffByMobileOrEmail(String type,String field,String accessToken) throws Exception{
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("type", type);
        params.put("field", field);
        String requestUrl = apiHost + URL_SEARCH_STAFF;
        String result = HttpClientUtil.get(requestUrl,params);
        logger.info("searchStaffByMobileOrEmail result:{}", result);
        return result;
    }
}
