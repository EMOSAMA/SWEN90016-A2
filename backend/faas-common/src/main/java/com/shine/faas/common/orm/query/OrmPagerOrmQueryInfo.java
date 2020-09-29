package com.shine.faas.common.orm.query;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by adam2 on 2019/5/7.
 * 分页查询信息
 */
public class OrmPagerOrmQueryInfo extends OrmQueryInfo {

    public OrmPagerOrmQueryInfo() {

    }

    public OrmPagerOrmQueryInfo(String name) {
        super(name);
    }

    public OrmPagerOrmQueryInfo(JsonNode jsonNode) {
        super(jsonNode);

        JsonNode pinfo = jsonNode.get("pagerInfo");
        if (pinfo == null) return;
        this.pagerInfo = new PagerInfo(pinfo);
    }

    private PagerInfo pagerInfo = new PagerInfo();

    //region get set

    public PagerInfo getPagerInfo() {
        return pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    //endregion


}
