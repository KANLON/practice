package com.kanlon.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 分页参数
 *
 * @author zhangcanlong
 * @since 2019-04-15 14:03
 **/
public class PageModel {
    /**
     * 每页大小
     */
    private int page = 1;
    /**
     * 每页条数
     */
    @JsonProperty("pageSize")
    private int pageSize = 10000;
    /**
     * 开始的条数，从0开始
     */
    private int start;


    public int getPage() {
        page = page <= 0 ? 1 : page;
        return page;
    }

    public void setPage(int page) { this.page = page; }

    public int getPageSize() {
        pageSize = pageSize > 1000 ? 1000 : pageSize;
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        start = (this.getPage() - 1) * this.getPageSize();
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
