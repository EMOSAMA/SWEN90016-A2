package com.shine.faas.common.orm.query;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * Created by adam2 on 2019/5/7.
 */
public class OrderBy {

    public final static String ASC = "asc";
    public final static String DESC = "desc";

    /**
     * 实体字段
     */
    private String field;

    /**
     * 升序，默认是升序
     */
    private String order = "asc";

    public OrderBy() {
    }

    public OrderBy(String field, Boolean isAsc) {
        this.field = field;
        this.order = isAsc ? "asc" : "desc";
    }

    public OrderBy(String field, String order) {
        this.field = field;
        this.order = order;
    }

    public OrderBy(JsonNode jsonNode) {
        this.field = jsonNode.get("field").asText();
        this.order = jsonNode.get("order").asText();
    }

    //region get set

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    //endregion

    public String toOrderByQL() {
        String ret = "";
        String var = this.field;

//        if (this.field.equals(LOCALNAME)) {
//            var = "(REPLACE(str(?i),\".*#\",\"\")"; //前提是当前类的实例变量为？i
//        }

        ret += var + " " + order + " ";
        return ret;
    }
}
