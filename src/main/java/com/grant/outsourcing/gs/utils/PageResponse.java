package com.grant.outsourcing.gs.utils;

import com.github.pagehelper.PageInfo;
import java.util.List;

public class PageResponse<T> {
    private Integer currentPage;

    private Integer pageSize;

    private Long totalNum;

    private Boolean firstPage;

    private Boolean lastPage;

    private Integer totalPage;

    private List items;

    public PageResponse(List<T> beans) {
        PageInfo pageInfo = new PageInfo(beans);
        this.currentPage = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalNum = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
        this.items = pageInfo.getList();
        this.firstPage = pageInfo.isIsFirstPage();
        this.lastPage = pageInfo.isIsLastPage();
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Boolean getFirstPage() {
        return this.firstPage;
    }

    public void setFirstPage(Boolean firstPage) {
        this.firstPage = firstPage;
    }

    public Boolean getLastPage() {
        return this.lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

    public List getItems() {
        return this.items;
    }

    public void setItems(List items) {
        this.items = items;
    }
}
