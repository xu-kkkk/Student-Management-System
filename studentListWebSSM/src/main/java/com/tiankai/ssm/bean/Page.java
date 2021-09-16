package com.tiankai.ssm.bean;

import java.util.List;

/**
 * 分页模型对象
 * 必须先设置pageTotal，后设置pageNo!
 *
 * @author: xutiankai
 * @date: 7/31/2021 12:35 PM
 */
public class Page<T> {
    // 默认情况下，每页显示4条数据
    public static final int INIT_PAGE_SIZE = 4;

    // 当前页码
    private Integer pageNo;
    // 总页数
    private Integer pageTotal;
    // 每页数据条数
    private Integer pageSize;
    // 总的记录数
    private Integer pageItemTotalCount;
    // 当前页的数据
    private List<T> items;
    // 前台地址，用于分页条页码跳转的地址
    private String url;

    public Page() {

    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (pageTotal != null && pageNo > pageTotal) {
            this.pageNo = pageTotal;
        } else {
            this.pageNo = pageNo;
        }
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPageItemTotalCount() {
        return pageItemTotalCount;
    }

    public void setPageItemTotalCount(Integer pageItemTotalCount) {
        this.pageItemTotalCount = pageItemTotalCount;
    }
}
