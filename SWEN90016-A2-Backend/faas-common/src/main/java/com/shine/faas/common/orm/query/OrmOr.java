package com.shine.faas.common.orm.query;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam2 on 2019/5/7.
 */
public class OrmOr extends OrmCondition {

    public OrmOr() {

    }

    public OrmOr(JsonNode jsonNode) {
        JsonNode andJS = jsonNode.get("andList");
        if (andJS == null) return;

        for (int i = 0; i < andJS.size(); i++) {
            this.andList.add(new OrmAnd(andJS.get(i)));
        }
    }

    public OrmAnd addAnd(){
        OrmAnd ormAnd = new OrmAnd();
        this.andList.add(ormAnd);
        return ormAnd;
    }

    private List<OrmAnd> andList = new ArrayList<>();

    //region get set

    public List<OrmAnd> getAndList() {
        return andList;
    }

    public void setAndList(List<OrmAnd> andList) {
        this.andList = andList;
    }

    //endregion
}
