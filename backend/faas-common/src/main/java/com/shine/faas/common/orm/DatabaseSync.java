package com.shine.faas.common.orm;

import com.fasterxml.jackson.databind.JsonNode;
import com.shine.faas.common.information.JsonObject;
import com.shine.faas.common.util.IndentStringBuilder;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.util.SpringUtil;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseSync {

    public void sync(DbContext dbContext, List<BaseEntity> entities) {

        NamedParameterJdbcTemplate jdbcTemplate = SpringUtil.getApplicationContext().getBean(NamedParameterJdbcTemplate.class);
        List<String> tables = new ArrayList<>();
        entities.forEach(baseEntity -> {
            tables.add(baseEntity.getTable());
        });

        String sql = "SELECT tablename FROM pg_tables where schemaname = 'public'";
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql, new HashMap<String, Object>());

        List<String> tables_exsited = new ArrayList<>();
        List<String> tables_not_exsited = new ArrayList<>();

        while (resultSet.next()) {
            tables_exsited.add(resultSet.getString("tablename"));
        }

        tables.forEach(table -> {
            if (!tables_exsited.contains(table))
                tables_not_exsited.add(table);
        });

        IndentStringBuilder builder = new IndentStringBuilder();
        tables_not_exsited.forEach(table -> {
            entities.forEach(entity -> {
                if (entity.getTable().equals(table))
                    this.addTable(builder, entity);
            });
        });

        if (tables_exsited.size() > 0) {
            String sql_columns = "SELECT c.relname as tablename, a.attnum,\n" +
                    "a.attname AS field,\n" +
                    "t.typname AS type,\n" +
                    "a.attlen AS length,\n" +
                    "a.atttypmod AS lengthvar,\n" +
                    "a.attnotnull AS notnull,\n" +
                    "b.description AS comment\n" +
                    "FROM pg_class c,\n" +
                    "pg_attribute a\n" +
                    "LEFT OUTER JOIN pg_description b ON a.attrelid=b.objoid AND a.attnum = b.objsubid,\n" +
                    "pg_type t\n" +
                    "WHERE c.relname in(";
            for (int i = 0; i < tables_exsited.size(); i++) {
                String table = tables_exsited.get(i);
                if (i > 0)
                    sql_columns += ",";
                sql_columns += "'" + table + "'";
            }
            sql_columns += "";
            sql_columns += ")\n" +
                    "and a.attnum > 0\n" +
                    "and a.attrelid = c.oid\n" +
                    "and a.atttypid = t.oid\n" +
                    "ORDER BY tablename, a.attnum;";
            SqlRowSet resultSet_cols = jdbcTemplate.queryForRowSet(sql_columns, new HashMap<String, Object>());
            List<JsonObject> jsonObjects = new ArrayList<>();
            String[] cols = resultSet_cols.getMetaData().getColumnNames();
            while (resultSet_cols.next()) {
                JsonObject jsonObject = new JsonObject();
                for (int i = 0; i < cols.length; i++) {
                    jsonObject.put(cols[i], resultSet_cols.getObject(cols[i]));
                }

                jsonObjects.add(jsonObject);
            }

            tables_exsited.forEach(table -> {
                entities.forEach(entity -> {
                    List<JsonObject> columns = jsonObjects.stream().filter(row -> row.get("tablename").equals(table)).collect(Collectors.toList());
                    if (entity.getTable().equals(table))
                        this.updateTable(builder, entity, columns);
                });
            });

        }

        System.out.println(builder.getContent());

        jdbcTemplate.update(builder.getContent(), new HashMap<>());
    }

    private void addTable(IndentStringBuilder builder, BaseEntity entity) {
        builder.appendFormatLine("create table {0} (", entity.getTable());
        builder.appendFormatLine("\"id\" uuid NOT NULL,");
        List<FieldInfo> fieldInfos = entity.getFields(entity.getClass());
        fieldInfos = fieldInfos.stream().filter(f -> !f.getName().equals("id")).collect(Collectors.toList());
        for (int i = 0; i < fieldInfos.size(); i++) {
            if (i > 0)
                builder.appendLine(",");
            FieldInfo fieldInfo = fieldInfos.get(i);
            if (fieldInfo.getName().equals("id"))
                continue;
            else {
                builder.appendFormat("\"{0}\" {1}", fieldInfo.getName(), getColumnType(fieldInfo));
//                if (fieldInfo.getPrecision() != null) {
//                    if (fieldInfo.getField().getType().equals(BigDecimal.class)) {
//                        builder.appendFormat("({0},{1})", "19", fieldInfo.getPrecision().toString());
//                    }
//                }
            }
        }
        builder.appendFormatLine(")");
        builder.appendFormatLine("WITH (OIDS=FALSE);");
        System.out.println(entity.getTable());
    }

    private String getColumnType(FieldInfo fieldInfo) {

        Class fldType = fieldInfo.getField().getType();
        if (fldType.equals(Double.class)) {
            return "float8";
        } else if (fldType.equals(Float.class)) {
            return "float4";
        } else if (fldType.equals(BigDecimal.class)) {
            return "numeric(19,"+ fieldInfo.getPrecision() + ")";
        } else if (fldType.equals(Integer.class)) {
            return "int4";
        } else if (fldType.equals(Long.class)) {
            return "int8";
        } else if (fldType.equals(String.class)) {
            return "varchar(" + fieldInfo.getLength() + ")";
        } else if (fldType.equals(UUID.class)) {
            return "uuid";
        } else if (fldType.equals(Date.class)) {
            return "timestamp(6)";
        } else if (fldType.equals(Boolean.class))
            return "bool";
        else if (fldType.equals(JsonNode.class))
            return "jsonb";

        return null;
    }

    private void updateTable(IndentStringBuilder builder, BaseEntity entity, List<JsonObject> columns) {
        //只负责新增或更改列类型(可兼容类型)

        List<FieldInfo> fieldInfos_exsited = new ArrayList<>();
        List<FieldInfo> fieldInfos = entity.getFields(entity.getClass());
        fieldInfos.forEach(fieldInfo -> {
            columns.forEach(column -> {
                if (fieldInfo.getName().equals(column.get("field"))) {
                    fieldInfos_exsited.add(fieldInfo);
                }
            });
        });

        List<FieldInfo> fieldInfos_new = fieldInfos.stream().filter(
                fieldInfo -> !fieldInfos_exsited.contains(fieldInfo)).collect(Collectors.toList());

        fieldInfos_new.forEach(fieldInfo -> {
            builder.appendFormatLine("ALTER TABLE {0} ADD COLUMN {1} {2};", entity.getTable(), fieldInfo.getName(), getColumnType(fieldInfo));
        });

        List<FieldInfo> fieldInfos_modify = fieldInfos_exsited.stream().filter(fieldInfo -> {
            JsonObject columnObj = columns.stream().filter(column ->
                    {
                        return column.get("field").equals(fieldInfo.getName());
                    }
            ).collect(Collectors.toList()).get(0);

            return !getColumnType(fieldInfo).equals(getColumnType(columnObj));
        }).collect(Collectors.toList());

        fieldInfos_modify.forEach(fieldInfo -> {
            JsonObject columnObj = columns.stream().filter(column ->
                    {
                        return column.get("field").equals(fieldInfo.getName());
                    }
            ).collect(Collectors.toList()).get(0);
            System.out.println("表: " + entity.getTable() + " 的字段" + fieldInfo.getName()
                    + "类型发生变化: " + getColumnType(fieldInfo) + " -> " + getColumnType(columnObj));
        });

    }

    private String getColumnType(JsonObject dbColDefine) {
        String type = dbColDefine.getString("type");
        Integer length = dbColDefine.getInteger("length");
        Integer lengthvar = dbColDefine.getInteger("lengthvar");
        String rslt = type;
        if (type.equals("varchar")) {
            lengthvar -= 4;
            rslt += "(" + lengthvar + ")";
        } else if (type.equals("numeric")) {
            rslt += "(19,2)";
        } else if (type.equals("timestamp")) {
            rslt += "(6)";
        }
        return rslt;
    }
}
