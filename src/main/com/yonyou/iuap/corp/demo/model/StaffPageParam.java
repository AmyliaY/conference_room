package com.yonyou.iuap.corp.demo.model;

import java.util.List;

public class StaffPageParam {

    private Integer index = 1;

    private Integer size = 20;

    /**
     * 排序
     */
    private List<SortParam> orders;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<SortParam> getOrders() {
        return orders;
    }

    public void setOrders(List<SortParam> orders) {
        this.orders = orders;
    }

}
