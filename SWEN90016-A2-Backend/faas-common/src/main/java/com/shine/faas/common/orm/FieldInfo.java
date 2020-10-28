package com.shine.faas.common.orm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldInfo {

    private String name;
    private Integer length;
    private Integer precision;
    private Integer scale;
    /**
     * java bean的字段
     */
    private Field field;

    private Method getMethod;

    public Method getGetMethod() {
        return getMethod;
    }

    public Method getSetMethod() {
        return setMethod;
    }

    private Method setMethod;

    //region get set

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }

    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    //endregion

    public FieldInfo() {

    }

    public Object getValue(Object obj) throws Exception {
        return this.getMethod.invoke(obj);
    }

    public FieldInfo(String name, Field field) {
        this.name = name;
        this.field = field;
    }
}
