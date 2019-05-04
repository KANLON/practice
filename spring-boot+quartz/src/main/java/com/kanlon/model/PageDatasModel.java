package com.kanlon.model;

/***
 * 分页数据返回
 * @author zhangcanlong
 * @since 2-10-04-15 14:04
 **/
public class PageDatasModel {
    private Long totalSize;
    private Object datas;
    private int page;
    private int pageSize;
    private int start;
    private PageModel pageModel;

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }

    public PageDatasModel(PageModel pageModel) {
        this.page = pageModel.getPage();
        this.pageSize = pageModel.getPageSize();
        this.start = pageModel.getStart();
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
