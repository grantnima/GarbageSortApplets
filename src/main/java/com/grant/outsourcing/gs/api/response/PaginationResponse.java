package com.grant.outsourcing.gs.api.response;

import java.util.ArrayList;
import java.util.List;

public class PaginationResponse<T> {

    private long total;

    private int pageSize;

    private int pageNum;

    private List<T> result;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public static <A, B> PaginationResponse<B> convertTo(PaginationResponse<A> aPage, IPaginationConverter<A, B> converter) {
        if (aPage == null) {
            return null;
        }
        PaginationResponse<B> bPage = new PaginationResponse<>();
        bPage.setPageSize(aPage.getPageSize());
        bPage.setPageNum(aPage.getPageNum());
        bPage.setTotal(aPage.getTotal());
        List<B> bList = new ArrayList<>();
        List<A> aList = aPage.getResult();
        if (aList != null) {
            for (A aItem: aList) {
                B b = converter.convertBy(aItem);
                if (b != null) {
                    bList.add(b);
                }
            }
        }
        bPage.setResult(bList);
        return bPage;
    }

    public interface IPaginationConverter<A, B> {
        B convertBy(A a);
    }
}

