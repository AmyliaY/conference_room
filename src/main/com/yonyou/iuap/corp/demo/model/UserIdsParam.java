package com.yonyou.iuap.corp.demo.model;

import java.util.List;

/**
 * 描述: 根据用户ids查询用户信息请求参数
 *
 * @author mantan
 * @create 2019-08-06 下午1:21
 */
public class UserIdsParam {
    private List<String> userIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
