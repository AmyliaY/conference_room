package com.yonyou.iuap.corp.demo.web;

import com.yonyou.iuap.corp.demo.model.*;
import com.yonyou.iuap.corp.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 描述: api接口
 *
 * @author mantan
 * @create 2019-04-09 下午1:02
 */
@RestController
public class ApiController {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private ApiFreeLoginService apiFreeLoginService;

    @Autowired
    private ApiStaffService apiStaffService;

    @Autowired
    private ApiUserService apiUserService;

    @Autowired
    private ApiToDoCenterService apiToDoCenterService;

    @Autowired
    private ApiMessageService apiMessageService;

    @Autowired
    private ApiJsApiService apiJsApiService;

    /**
     * 获得调用接口令牌access_token
     *
     * @return
     * @throws Exception
     */
    @GetMapping("avail")
    public Object avail() throws Exception {
        return "200 ok";
    }


    /**
     * 获得调用接口令牌access_token
     *
     * @return
     * @throws Exception
     */
    @GetMapping("get_access_token")
    public Object getAccessToken() throws Exception {
        return accessTokenService.getAccessToken();
    }

    /**
     * 获取jsticket
     *
     * @return
     * @throws Exception
     */
    @GetMapping("get_jsticket")
    public Object getJsticket() throws Exception {
        return apiJsApiService.getJsTicket(accessTokenService.getAccessToken());
    }

    /**
     * 生成jsapi鉴权信息
     *
     * @return
     * @throws Exception
     */
    @GetMapping("get_agentInfo")
    public Map<String, Object> getAgentInfo() throws Exception {
        return apiJsApiService.getAgentInfo(accessTokenService.getAccessToken());
    }

    /**
     * 根据code获取免登信息
     *
     * @param code
     * @param userMobileEmailFlag
     * @return
     * @throws Exception
     */
    @GetMapping("get_free_login_info")
    public Object getFreeLoginInfo(@RequestParam String code, @RequestParam(required = false, defaultValue = "false") String userMobileEmailFlag) throws Exception {
        return apiFreeLoginService.getFreeLoginInfo(code, userMobileEmailFlag, accessTokenService.getAccessToken());
    }

    /**
     * 分页查询当前租户下所有用户
     *
     * @param pageParam
     * @return
     * @throws Exception
     */
    @PostMapping("get_user_page_list")
    public Object getUserPageList(@RequestBody UserPageParam pageParam) throws Exception {
        return apiUserService.getUserPageList(pageParam, accessTokenService.getAccessToken());
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param userIdsParam
     * @return
     * @throws Exception
     */
    @PostMapping("get_user_list_by_ids")
    public Object getUserListByIds(@RequestBody UserIdsParam userIdsParam) throws Exception {
        return apiUserService.listByIds(userIdsParam, accessTokenService.getAccessToken());
    }

    /**
     * 根据手机号/邮箱/用户名分页查询当前租户下用户
     *
     * @param pageParam
     * @return
     * @throws Exception
     */
    @PostMapping("search_user")
    public Object searchUser(@RequestBody UserPageParam pageParam) throws Exception {
        return apiUserService.searchUser(pageParam, accessTokenService.getAccessToken());
    }

    /**
     * 分页查询当前租户员工列表
     *
     * @param staffPageParam
     * @return
     * @throws Exception
     */
    @PostMapping("get_staff_page_list")
    public Object getStaffPageList(@RequestBody StaffPageParam staffPageParam) throws Exception {
        return apiStaffService.getStaffPageList(staffPageParam, accessTokenService.getAccessToken());
    }

    /**
     * 根据手机号或邮箱查询员工信息
     *
     * @param type  查询类型：1-手机，2-邮箱
     * @param field 查询值
     * @return
     * @throws Exception
     */
    @GetMapping("search_staff_by_mobile_email")
    public Object searchStaffByMobileOrEmail(@RequestParam String type, @RequestParam String field) throws Exception {
        return apiStaffService.searchStaffByMobileOrEmail(type, field, accessTokenService.getAccessToken());
    }


    /**
     * 发送工作通知
     *
     * @param notifyShareContent
     * @return
     * @throws Exception
     */
    @PostMapping("send_notify_share")
    public Object sendNotifyShare(@RequestBody NotifyShareContent notifyShareContent) throws Exception {
        return apiMessageService.sendNotifyShare(notifyShareContent, accessTokenService.getAccessToken());
    }


    /**
     * 发送待办事件
     *
     * @param toDoContent
     * @return
     * @throws Exception
     */
    @PostMapping("send_todo")
    public Object sendToDo(@RequestBody ToDoContent toDoContent) throws Exception {
        return apiToDoCenterService.sendToDo(toDoContent, accessTokenService.getAccessToken());
    }

    /**
     * 标记待办事件为已处理
     *
     * @param toDoContent
     * @return
     * @throws Exception
     */
    @PostMapping("send_done")
    public Object sendDone(@RequestBody ToDoContent toDoContent) throws Exception {
        return apiToDoCenterService.sendDone(toDoContent, accessTokenService.getAccessToken());
    }

    /**
     * 删除待办事件
     *
     * @param toDoContent
     * @return
     * @throws Exception
     */
    @PostMapping("revocation")
    public Object revocation(@RequestBody ToDoContent toDoContent) throws Exception {
        return apiToDoCenterService.revocation(toDoContent, accessTokenService.getAccessToken());
    }

}
