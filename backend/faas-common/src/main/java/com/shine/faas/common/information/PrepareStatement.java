package com.shine.faas.common.information;

//import com.kb.study.orm.ParameterMatcher;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

public class PrepareStatement {
    private String sql;
//    private JsonArray jdbcParameters = new JsonArray();
    private JsonObject jdbcParameters = new JsonObject();

    //region get set

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public JsonObject getJdbcParameters() {
        return jdbcParameters;
    }

    public void setJdbcParameters(JsonObject jdbcParameters) {
        this.jdbcParameters = jdbcParameters;
    }

    //endregion

    public void fill(String sql, JsonObject parameters) {
        this.sql = sql;
        this.jdbcParameters = parameters;
//        Pattern pattern = Pattern.compile("(:(\\d|[a-z]|[A-Z]|\\.|_|-)+)");
//        Matcher matcher = pattern.matcher(sql);
//        List<ParameterMatcher> matchers = new ArrayList<ParameterMatcher>();
//        while (matcher.find()) {
//            String g = matcher.group().replace(":", "");
//            ParameterMatcher parameterMatcher = new ParameterMatcher();
//            parameterMatcher.setName(g);
//            parameterMatcher.setStart(matcher.start());
//            parameterMatcher.setEnd(matcher.end());
//            matchers.add(parameterMatcher);
////            System.out.println(g);
//        }
//
//        matchers = matchers.stream().sorted((x, y) -> x.getStart() - y.getStart()).collect(Collectors.toList());
//        sql += " ";
//        String newSql = sql;
//
//        Integer offset = 0;
//        for (ParameterMatcher m : matchers) {
//            Object obj = parameters.get(m.getName());
//            Integer old = sql.length();
//            String value = obj.toString();
//            if (obj instanceof ArrayNode) {
//                value = toList(((ArrayNode) obj).elements());
//                newSql = newSql.substring(0, m.getStart() + offset) + value + newSql.substring(m.getEnd() + offset);
//                offset = newSql.length() - old;
//            }  else if (obj instanceof Number) {
//                value = "?";
//                newSql = newSql.substring(0, m.getStart() + offset) + value + newSql.substring(m.getEnd() + offset);
//                offset = newSql.length() - old;
//                this.jdbcParameters.add(obj);
//            } else if (obj instanceof String) {
//                value = "?";
//                if (sql.substring(m.getStart() - 1, m.getStart()).equals("%") && sql.substring(m.getEnd(), m.getEnd() + 1).equals("%")) {
//                    this.jdbcParameters.add("%" + obj + "%");
//                    newSql = newSql.substring(0, m.getStart() + offset - 2) + value + newSql.substring(m.getEnd() + offset + 2);
//                    offset = newSql.length() - old;
//                } else if (sql.substring(m.getStart() - 1, m.getStart()).equals("%")) {
//                    this.jdbcParameters.add("%" + obj);
//                    newSql = newSql.substring(0, m.getStart() + offset - 2) + value + newSql.substring(m.getEnd() + offset);
//                    offset = newSql.length() - old;
//                } else if (sql.substring(m.getEnd(), m.getEnd() + 1).equals("%")) {
//                    this.jdbcParameters.add(obj + "%");
//                    newSql = newSql.substring(0, m.getStart() + offset) + value + newSql.substring(m.getEnd() + offset + 2);
//                    offset = newSql.length() - old;
//                } else {
//                    this.jdbcParameters.add(obj);
//                    newSql = newSql.substring(0, m.getStart() + offset) + value + newSql.substring(m.getEnd() + offset);
//                    offset = newSql.length() - old;
//                }
//            }
//        }
//
//        this.sql = newSql.toString();
//        System.out.println(newSql);
    }

    private String toList(Iterator<JsonNode> list) {
        if (!list.hasNext())
            return "()";

        StringBuilder builder = new StringBuilder();
        builder.append("(");
        Object val = list.next();
        for (int i = 0; list.hasNext(); i++) {
            if (i > 0)
                builder.append(",");
            JsonNode node = list.next();
            if (val instanceof UUID) {
                builder.append("'" + node.toString() + "'");
            } else if (val instanceof Date) {
                builder.append("'" + node.toString() + "'");
            } else if (val instanceof String) {
                builder.append("'" + node.toString() + "'");
            } else if (val instanceof Number) {
                builder.append(node.toString());
            }
        }

        builder.append(")");
        return builder.toString();
    }
}
