package com.yonyou.iuap.corp.demo.model;

/**
 * 分页查询参数
 */
public class UserPageParam {
    /**
     * 第几页
     */
    public String index;
    /**
     * 一页大小
     */
    public String size;
    /**
     * 排序字段，默认按字段升序排列
     */
    public String sortType;
    /**
     * 查询条件(手机号/邮箱/用户名)
     */
    public String searchcode;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSearchcode() {
        return searchcode;
    }

    public void setSearchcode(String searchcode) {
        this.searchcode = searchcode;
    }
}
