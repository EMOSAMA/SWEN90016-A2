package com.shine.faas.common.orm.query;

/**
 * Created by adam2 on 2019/5/7.
 */
public abstract class OrmCondition {

    private QueryInternalInfo queryInternalInfo;

    //region get set

    public QueryInternalInfo getQueryInternalInfo() {
        return queryInternalInfo;
    }

    public void setQueryInternalInfo(QueryInternalInfo queryInternalInfo) {
        this.queryInternalInfo = queryInternalInfo;
    }

    //endregion
}
