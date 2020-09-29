package com.shine.faas.common;

//import rx.Single;

import java.util.HashMap;
import java.util.Map;

public class DbContext{

    private Map<String,Object> datas = new HashMap<>();

    private Boolean isAutoCommit = true;
//    private NamedParameterJdbcTemplate jdbcTemplate;

    public Boolean getAutoCommit() {
        return isAutoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        isAutoCommit = autoCommit;
    }

    public SQLConnection getConnection() {

        return ((SQLConnection) datas.get("CONNECTION"));
    }


    public void setConnection(SQLConnection connection){
        datas.put("CONNECTION",connection);
    }

//    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public NamedParameterJdbcTemplate getJdbcTemplate() {
//        return jdbcTemplate;
//    }
}
