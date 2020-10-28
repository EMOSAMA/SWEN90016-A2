package com.shine.faas.common.orm.query;

import com.fasterxml.jackson.databind.JsonNode;

public class PagerInfo {
    /**
     * 页大小
     */
    private int pageSize = 10;

    /**
     * 页索引
     */
    private int pageIndex = 1;

    public PagerInfo() {

    }

    public PagerInfo(JsonNode jsonNode) {
        this.pageSize = jsonNode.get("pageSize").asInt();
        this.pageIndex = jsonNode.get("pageIndex").asInt();
    }

    //region get set

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    //endregion

    public String toPagerQL() {
        String ret = String.format("LIMIT %d OFFSET %d ", pageSize, ((pageIndex - 1) * pageSize));
        return ret;
    }
}