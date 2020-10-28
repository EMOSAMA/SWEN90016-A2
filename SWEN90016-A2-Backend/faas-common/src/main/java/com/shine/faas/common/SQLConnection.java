package com.shine.faas.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.shine.faas.common.information.JsonObject;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLConnection {

    NamedParameterJdbcTemplate jdbcTemplate;
    private Connection connection = null;

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (autoCommit == true) return;

        if (!TransactionSynchronizationManager.isSynchronizationActive())
            TransactionSynchronizationManager.initSynchronization();

        HikariDataSource dataSource = (HikariDataSource) (jdbcTemplate.getJdbcTemplate()).getDataSource();//.getJdbcOperations()).getDataSource();
        connection = DataSourceUtils.getConnection(dataSource);
        connection.setAutoCommit(autoCommit);
        this.autoCommit = autoCommit;

    }

    private boolean autoCommit = true;

    public SQLConnection(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer batchWithParams(String sql, List<JsonObject> objectList) throws Exception {
        Map<String, Object>[] maps = new Map[objectList.size()];
        for (int j = 0; j < objectList.size(); j++) {
            Map<String, Object> map = maps[j] = new HashMap<>();
            Map<String, Object> sourceMap = objectList.get(j).toMap();
            for (String k : sourceMap.keySet()) {
                Object v = sourceMap.get(k);
                if (v instanceof JsonNode) {
                    //支持jsonb的存储
                    PGobject pGobject = new PGobject();
                    pGobject.setType("jsonb");
                    pGobject.setValue(v.toString());
                    map.put(k, pGobject);
                } else {
                    map.put(k, v);
                }
            }
        }

        return this.jdbcTemplate.batchUpdate(sql, maps).length;
    }

    public SqlRowSet queryWithParams(String sql, JsonObject jdbcParameters) throws SQLException {
        return this.jdbcTemplate.queryForRowSet(sql, jdbcParameters.toMap());
    }

    public Integer updateWithParams(String sql, JsonObject jdbcParameters) {
        return this.jdbcTemplate.update(sql, jdbcParameters.toMap());
    }

    public void update(String content) {

    }

    public void close() throws Exception {
        if (connection != null) {
//            TransactionSynchronizationManager.clearSynchronization();
            connection.setAutoCommit(true);
        }
//        if (this.connection != null)
//            this.connection.close();
    }

    public void rollback() throws Exception {
        if (connection != null)
            connection.rollback();
    }

    public void commit() throws Exception {
        if (connection != null)
            connection.commit();
    }

    public void reset() throws Exception {
        if (connection != null) {
            TransactionSynchronizationManager.clearSynchronization();
            connection.setAutoCommit(true);
//            HikariDataSource dataSource = (HikariDataSource)( jdbcTemplate.getJdbcTemplate()).getDataSource();
//            dataSource.evictConnection(connection);
        }
    }
}
