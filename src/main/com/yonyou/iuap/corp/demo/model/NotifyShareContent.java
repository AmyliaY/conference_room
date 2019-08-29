package com.yonyou.iuap.corp.demo.model;

import java.util.List;

/**
 * 描述: 工作通知消息体
 *
 * @author mantan
 * @create 2019-04-09 下午4:51
 */
public class NotifyShareContent extends CommonContent{

    /**
     * 有地址时点击跳转: 移动端打开地址
     */
    private String url;

    /**
     * 有地址时点击跳转: WEB打开地址
     */
    private String webUrl;

    /**
     * 接收范围，list根据to发送
     */
    private String sendScope;

    /**
     * sendScope=list时，接收人范围，yhtUserIds列表结构，用户ID列表
     */
    private List<String> yhtUserIds;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSendScope() {
        return sendScope;
    }

    public void setSendScope(String sendScope) {
        this.sendScope = sendScope;
    }

    public List<String> getYhtUserIds() {
        return yhtUserIds;
    }

    public void setYhtUserIds(List<String> yhtUserIds) {
        this.yhtUserIds = yhtUserIds;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
