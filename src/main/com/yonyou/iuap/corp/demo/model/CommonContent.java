package com.yonyou.iuap.corp.demo.model;

/**
 * 通用的消息体参数
 */
public class CommonContent {

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 文本内容
     */
    private String title;

    /**
     * 文本描述
     */
    private String content;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
