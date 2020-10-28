package com.shine.faas.common.orm;

public class ParameterMatcher {
    private String name;
    private Integer start;
    private Integer end;

    //region get set

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    //endregion
}
