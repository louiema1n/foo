package com.lm.domain;

import java.util.List;

/**
 * 页面请求结果实体
 * Created by Louie on 2017-06-28.
 */
public class PageResult {

    private long total;  // 总数
    private List<?> rows;   // 所有记录集合

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
