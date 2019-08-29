package com.yonyou.iuap.corp.demo.model;

import java.util.List;

/**
 * 待办消息体
 */
public class ToDoContent extends CommonContent{

    /**
     * 待办事件唯一KEY, 用来标记为已处理或者删除使用，需要业务系统保证唯一性
     */
    private String businessKey;

    /**
     * 类型名称, 即移动端页签名称
     */
    private String typeName;

    /**
     * 友户通人员ID, 待办分配的人员，不能为空
     */
    private List<String> yyUserIds;

    /**
     * 移动端打开URL
     */
    private String mUrl;

    /**
     * WEB端或PC端打开URL
     */
    private String webUrl;

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<String> getYyUserIds() {
        return yyUserIds;
    }

    public void setYyUserIds(List<String> yyUserIds) {
        this.yyUserIds = yyUserIds;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
