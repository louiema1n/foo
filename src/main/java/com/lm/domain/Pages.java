package com.lm.domain;

import java.util.List;

/**
 * 页码
 * Created by Louie on 2017-08-07.
 */
public class Pages {
    private long total;  // 记录总数
    private long totalPage;  // 页码总数
    private List<Long> pages;    // 页码
    private int currentPage;       //当前页

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<Long> getPages() {
        return pages;
    }

    public void setPages(List<Long> pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
