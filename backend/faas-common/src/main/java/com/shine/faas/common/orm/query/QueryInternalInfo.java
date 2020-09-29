package com.shine.faas.common.orm.query;

import com.shine.faas.common.information.JsonObject;

public class QueryInternalInfo {
    private String filter = null;
    private String orderBy = null;
    private String pager = null;
    private PagerInfo pagerInfo = new PagerInfo();
    private JsonObject parameters = new JsonObject();

    //region get set

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getPager() {
        return pager;
    }

    public void setPager(String pager) {
        this.pager = pager;
    }

    public PagerInfo getPagerInfo() {
        return pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public JsonObject getParameters() {
        return parameters;
    }

    public void setParameters(JsonObject parameters) {
        this.parameters = parameters;
    }

    //endregion
}
