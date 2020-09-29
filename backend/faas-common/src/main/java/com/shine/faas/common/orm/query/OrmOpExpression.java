package com.shine.faas.common.orm.query;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.UUID;

//import com.kb.study.util.IndentStringBuilder;


/**
 * Created by adam2 on 2019/5/7.
 */
public class OrmOpExpression extends OrmCondition {

    public static final String OP_LT = "<";
    public static final String OP_LT_EQ = "<=";
    public static final String OP_GT = ">";
    public static final String OP_GT_EQ = ">=";
    public static final String OP_EQ = "=";
    public static final String OP_NOT_EQ = "!=";
    public static final String OP_IN = "in";
    public static final String OP_NOT_IN = "not in";
    public static final String OP_STR_CONTAINS = "contains";
    public static final String OP_STR_STARTS = "statsWith";
    public static final String OP_STR_ENDS = "endsWith";
    public static final String OP_ISNULL = "isNull";
    public static final String OP_ISNOTNULL = "isNotNull";

    private String field;
    private String op;

    /**
     * 简单类型字面值或者jsonNode
     */
    private Object opValue;

    public OrmOpExpression() {

    }

    public OrmOpExpression(JsonNode jsonNode) {
        this.field = jsonNode.get("field").asText();
        this.op = jsonNode.get("op").asText();
        this.opValue = jsonNode.get("opValue");

    }

    public OrmOpExpression(String field, String op, Object opValue) {
        this.field = field;
        this.op = op;
        this.opValue = opValue;
    }

    //region get set

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Object getOpValue() {
        return opValue;
    }

    public void setOpValue(Object opValue) {
        this.opValue = opValue;
    }

    //endregion

    public String toOpQL(QueryInternalInfo queryInternalInfo) {
        String ret = "";
        String var = this.field;

//        optionBuilder.appendFormat(" {0} {1} {2} " ,this.field,this.op);

        String value = ":" + this.field;// QueryAssitant.getValue(op, dateType, opValue);
        value += UUID.randomUUID().toString().replace("-","");
        int like_index = 0;
        if (this.op.equals(OP_LT)
                || this.op.equals(OP_LT_EQ)
                || this.op.equals(OP_GT)
                || this.op.equals(OP_GT_EQ)
                || this.op.equals(OP_EQ)
                || this.op.equals(OP_NOT_EQ)) {
            ret += var + " " + op + " " + value;
        } else if (this.op.equals(OP_STR_CONTAINS)) {
            value += like_index++;
            ret = var + " LIKE " + " " + value;
            opValue = "%" + this.opValue + "%";
        } else if (this.op.equals(OP_STR_STARTS)) {
            value += like_index++;
            ret = var + " LIKE " + " " + value;
            opValue = this.opValue + "%";
        } else if (this.op.equals(OP_STR_ENDS)) {
            value += like_index++;
            ret = var + " LIKE " + " " + value;
            opValue = "%" + this.opValue;
        } else if (this.op.equals(OP_ISNULL)) {
            ret = var + " IS NULL ";
        } else if (this.op.equals(OP_ISNOTNULL)) {
            ret = var + " IS NOT NULL ";
        } else if (this.op.equals(OP_IN)) {
            ret = var + " " + op + " " + value;
        } else if (this.op.equals(OP_NOT_IN)) {
            ret = var + " " + op + " " + value;
        }

        ret += " ";

//        if (opValue instanceof BigDecimal)
//            queryInternalInfo.getParameters().put(var, ((BigDecimal) opValue).doubleValue());
//        else if(like_index > 0)
//            queryInternalInfo.getParameters().put(value.substring(1), opValue);
//        else
//            queryInternalInfo.getParameters().put(var, opValue);
        if (opValue instanceof BigDecimal)
            queryInternalInfo.getParameters().put(value.substring(1), ((BigDecimal) opValue).doubleValue());
        else if(like_index > 0)
            queryInternalInfo.getParameters().put(value.substring(1), opValue);
        else
            queryInternalInfo.getParameters().put(value.substring(1), opValue);
        return ret;
    }
}