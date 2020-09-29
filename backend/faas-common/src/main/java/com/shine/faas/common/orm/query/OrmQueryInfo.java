package com.shine.faas.common.orm.query;

import com.fasterxml.jackson.databind.JsonNode;
import com.shine.faas.common.information.JsonObject;
import com.shine.faas.common.orm.QueryBuilder;
import com.shine.faas.common.orm.SqlParaJson;
import com.sun.javafx.tk.TKClipboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by adam2 on 2019/5/7.
 * 查询信息
 */
public class OrmQueryInfo {

    private JsonObject parameters = new SqlParaJson();
    /**
     * 占位查询，如果当前是占位查询，则不能再包括占位查询
     */
    private List<OrmQueryInfo> placeholderQueries = new ArrayList<>();
    /**
     * 查询的名称
     */
    private String name;
    /**
     * 内部查询信息
     */
    private QueryInternalInfo queryInternalInfo;

    public OrmQueryInfo() {

    }

    public OrmQueryInfo(String name) {
        this.name = name;
    }

    public OrmQueryInfo(JsonNode jsonNode) {
        JsonNode orJS = jsonNode.get("orList");
        if (orJS != null) {
            for (int i = 0; i < orJS.size(); i++) {
                this.orList.add(new OrmOr(orJS.get(i)));
            }
        }
        JsonNode orderbyJS = jsonNode.get("orderByList");
        if (orderbyJS != null) {
            for (int i = 0; i < orderbyJS.size(); i++) {
                this.orderByList.add(new OrderBy(orderbyJS.get(i)));
            }
        }
    }

    public OrmOr addOr() {
        OrmOr ormOr = new OrmOr();
        this.orList.add(ormOr);
        return ormOr;
    }

    public OrmQueryInfo addOrder(OrderBy orderBy) {
        this.orderByList.add(orderBy);
        return this;
    }

    private List<OrmOr> orList = new ArrayList<>();

    private List<OrderBy> orderByList = new ArrayList<>();

    //region get set


    public QueryInternalInfo fillQueryInternalInfo() {

        queryInternalInfo = QueryBuilder.convert(this);

        if (this.placeholderQueries.size() > 0) {
            this.placeholderQueries.forEach(queryInfo -> {
                Map<String, Object> objectMap = this.parameters.toMap();
                queryInfo.getParameters().toMap().forEach((k, v) -> {
                    if (objectMap.containsKey(k) && objectMap.get(k).equals(v))
                        return;
                    if (objectMap.containsKey(k) && !objectMap.get(k).equals(v))
                        throw new RuntimeException("同样的参数值必须一致");
                    objectMap.put(k, v);
                });
            });
        }

        return queryInternalInfo;
    }

    public void setQueryInternalInfo(QueryInternalInfo queryInternalInfo) {
        this.queryInternalInfo = queryInternalInfo;
    }

    public List<OrmQueryInfo> getPlaceholderQueries() {
        return Collections.unmodifiableList(placeholderQueries);
    }

    public void addPlaceholderQuery(OrmQueryInfo queryInfo) throws RuntimeException {
        if (queryInfo.getName() == null || queryInfo.getName().equals(""))
            throw new RuntimeException("占位查询必须有名称");
        if (this.getName() != null) {
            throw new RuntimeException("占位查询不能有子占位查询");
        }

        this.placeholderQueries.add(queryInfo);
    }

    public void removePlaceholderQuery(OrmQueryInfo queryInfo) {
        this.placeholderQueries.remove(queryInfo);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonObject getParameters() {
        return parameters;
    }

    public void setParameters(JsonObject parameters) {
        this.parameters = parameters;
    }

    public List<OrmOr> getOrList() {
        return orList;
    }

    public void setOrList(List<OrmOr> orList) {
        this.orList = orList;
    }

    public List<OrderBy> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<OrderBy> orderByList) {
        this.orderByList = orderByList;
    }

    //endregion

}
