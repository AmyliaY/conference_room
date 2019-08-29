package com.yonyou.iuap.corp.demo.model;

public class SortParam {

    /**
     * 排序字段
     */
    private String field;

    /**
     * 升降序标志
     */
    private Boolean asc = true;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Boolean getAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }
}
