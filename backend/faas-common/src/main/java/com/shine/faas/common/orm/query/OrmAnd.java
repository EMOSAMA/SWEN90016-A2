package com.shine.faas.common.orm.query;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam2 on 2019/5/7.
 */
public class OrmAnd extends OrmCondition {

    public OrmAnd() {
    }

    public OrmAnd(JsonNode jsonNode) {
        JsonNode conditionJS = jsonNode.get("conditionList");
        if (conditionJS == null) return;

        for (int i = 0; i < conditionJS.size(); i++) {
            JsonNode child = conditionJS.get(i);
            if (child.get("field") != null)
                this.conditionList.add(new OrmOpExpression(conditionJS.get(i)));
            else if (child.get("andList") != null)
                this.conditionList.add(new OrmOr(conditionJS.get(i)));
            else if (child.get("conditionList") != null)
                this.conditionList.add(new OrmAnd(conditionJS.get(i)));
        }
    }

    private List<OrmCondition> conditionList = new ArrayList<>();

    public OrmOr addOr() {
        OrmOr ormOr = new OrmOr();
        this.conditionList.add(ormOr);
        return ormOr;
    }

    public OrmOpExpression addOpExpression() {
        OrmOpExpression ormOpExpression = new OrmOpExpression();
        this.conditionList.add(ormOpExpression);
        return ormOpExpression;
    }

    public OrmOpExpression addOpExpression(String field, String op, Object opValue) {
        OrmOpExpression ormOpExpression = new OrmOpExpression(field, op, opValue);
        this.conditionList.add(ormOpExpression);
        return ormOpExpression;
    }

    //region get set

    public List<OrmCondition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<OrmCondition> conditionList) {
        this.conditionList = conditionList;
    }

    //endregion
}
