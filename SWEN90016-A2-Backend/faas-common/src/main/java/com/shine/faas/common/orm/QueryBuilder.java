package com.shine.faas.common.orm;

import com.shine.faas.common.orm.query.*;
import com.shine.faas.common.util.IndentStringBuilder;
import com.shine.faas.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryBuilder {

    public static String buildSql(String baseSql, OrmQueryInfo ormQueryInfo) {
        //设置分页信息
//        QueryInternalInfo internalInfo = convert(ormQueryInfo);
//        ormQueryInfo.setQueryInternalInfo(internalInfo);
        String sql = buildInnerSql(baseSql, ormQueryInfo);
        if (ormQueryInfo.getName() != null)
            return sql;
        return buildSql(sql, ormQueryInfo.fillQueryInternalInfo());
    }

    private static String buildInnerSql(String baseSql, OrmQueryInfo ormQueryInfo) {

        ormQueryInfo.fillQueryInternalInfo();
//        QueryInternalInfo queryInternalInfo = convert(ormQueryInfo);
//        ormQueryInfo.setQueryInternalInfo(queryInternalInfo);

        List<OrmQueryInfo> placeholderQueries = new ArrayList<>();
        if (ormQueryInfo.getName() != null) {
            placeholderQueries.add(ormQueryInfo);
        } else {
            placeholderQueries = ormQueryInfo.getPlaceholderQueries();
            placeholderQueries.forEach(placeholderQuery -> {
                if (placeholderQuery.getName() == null)
                    throw new RuntimeException("占位查询必须有名称");


//                QueryInternalInfo internalInfo = convert(placeholderQuery);
                placeholderQuery.fillQueryInternalInfo();


            });

        }

        List<String> holders = new ArrayList<>();
        //获取所有占位查询
        {
            String holderReg = "#\\{.*?\\}";
            Pattern pattern = Pattern.compile(holderReg);
            Matcher matcher = pattern.matcher(baseSql);
            List<ParameterMatcher> matchers = new ArrayList<ParameterMatcher>();

            while (matcher.find()) {
                String g = matcher.group();
                if (holders.contains(g))
                    continue;
                holders.add(g);
            }
        }

        //展开占位查询
        IndentStringBuilder builder = new IndentStringBuilder();
        String sql = baseSql;
        for (OrmQueryInfo placeholderQuery : placeholderQueries) {
            //找到占位位置
            String holderReg = "#\\{" + placeholderQuery.getName() + "\\}";
            Pattern pattern = Pattern.compile(holderReg);
            Matcher matcher = pattern.matcher(sql);
            List<ParameterMatcher> matchers = new ArrayList<ParameterMatcher>();

            while (matcher.find()) {
                String g = matcher.group();
                ParameterMatcher parameterMatcher = new ParameterMatcher();
                parameterMatcher.setName(g);
                parameterMatcher.setStart(matcher.start());
                parameterMatcher.setEnd(matcher.end());
                matchers.add(parameterMatcher);
                holders.remove(g);
            }

            if (matchers.size() > 1)
                throw new RuntimeException("占位符必须在查询中唯一存在");
            if (matchers.size() == 1) {
                ParameterMatcher parameterMatcher = matchers.get(0);
                sql = buildSql(sql.substring(0, parameterMatcher.getStart()), placeholderQuery.fillQueryInternalInfo())
                        + sql.substring(parameterMatcher.getEnd());
            }
        }

        //去掉未被使用的占位
        for (String s : holders) {
            sql = sql.replace(s, "");
        }

        return sql;
    }

    public static String buildCountSql(String baseSql, OrmQueryInfo ormQueryInfo) {
        //求总数
        IndentStringBuilder builder = new IndentStringBuilder();
        builder.increaseIndentLine("SELECT COUNT(1) AS count ");
        builder.increaseIndentLine("FROM (");

        builder.appendFormatLine(buildInnerSql(baseSql, ormQueryInfo));
        String upperSql = baseSql.toUpperCase();
        upperSql = upperSql.replaceAll("'.*'", "NULL");

        if (!StringUtil.isEmpty(ormQueryInfo.fillQueryInternalInfo().getFilter())) {

            if (upperSql.lastIndexOf("WHERE") > upperSql.lastIndexOf("SELECT"))
                builder.append(" AND ");
            else
                builder.append(" WHERE ");
            builder.append(ormQueryInfo.fillQueryInternalInfo().getFilter());
        }

        builder.decreaseIndentLine(")T");

        return builder.getContent();
    }

    private static final String buildSql(String baseSql, QueryInternalInfo queryInternalInfo) {
        //返回数据
        IndentStringBuilder builder = new IndentStringBuilder();
        builder.increaseIndentLine(baseSql);

        String upperSql = baseSql.toUpperCase();
        upperSql = upperSql.replaceAll("'.*'", "NULL");
        if (queryInternalInfo.getFilter() != null && queryInternalInfo.getFilter().length() > 1) {
            if (upperSql.lastIndexOf("WHERE") > upperSql.lastIndexOf("SELECT"))
                builder.append(" AND ");
            else
                builder.append(" WHERE ");
        }

        if (!StringUtil.isEmpty(queryInternalInfo.getFilter()))
            builder.append(queryInternalInfo.getFilter());

        if (!StringUtil.isEmpty(queryInternalInfo.getOrderBy()))
            builder.appendFormatLine(" ORDER BY " + queryInternalInfo.getOrderBy());

        if (!StringUtil.isEmpty(queryInternalInfo.getPager()))
            builder.appendFormatLine(queryInternalInfo.getPager());

        return builder.getContent();
    }

//    public static QueryInternalInfo convert(OrmPagerOrmQueryInfo ormQueryInfo) {
//        QueryInternalInfo queryInternalInfo = convert((OrmQueryInfo) ormQueryInfo);
//        if (ormQueryInfo.getPagerInfo() != null) {
//            queryInternalInfo.setPager(ormQueryInfo.getPagerInfo().toPagerQL());
//        }
//        return queryInternalInfo;
//    }

    public static QueryInternalInfo convert(OrmQueryInfo ormQueryInfo) {

        QueryInternalInfo queryInternalInfo = new QueryInternalInfo();
        if (ormQueryInfo.getParameters() != null)
            queryInternalInfo.setParameters(ormQueryInfo.getParameters());

        IndentStringBuilder optionBuilder = new IndentStringBuilder();
        if (ormQueryInfo.getOrList() != null) {
            IndentStringBuilder builder = new IndentStringBuilder();
            for (int i = 0; i < ormQueryInfo.getOrList().size(); i++) {
                if (i > 0)
                    builder.append(" OR ");
                buildOr(queryInternalInfo, builder, optionBuilder, ormQueryInfo.getOrList().get(i));
            }

            queryInternalInfo.setFilter(builder.getContent());
        }

        if (ormQueryInfo.getOrderByList() != null && ormQueryInfo.getOrderByList().size() > 0) {
            IndentStringBuilder builder = new IndentStringBuilder();
            for (int i = 0; i < ormQueryInfo.getOrderByList().size(); i++) {
                OrderBy orderBy = ormQueryInfo.getOrderByList().get(i);
                if (i > 0)
                    builder.append(",");
                builder.append(orderBy.toOrderByQL());
            }

            queryInternalInfo.setOrderBy(builder.getContent());
        }

        if (ormQueryInfo instanceof OrmPagerOrmQueryInfo) {
            queryInternalInfo.setPager(((OrmPagerOrmQueryInfo) ormQueryInfo).getPagerInfo().toPagerQL());
        }
        return queryInternalInfo;
    }

    private static void buildOr(QueryInternalInfo queryInternalInfo, IndentStringBuilder filterBuilder, IndentStringBuilder optionBuilder, OrmOr ormOr) {
        if (ormOr.getAndList() == null || ormOr.getAndList().size() == 0)
            return;

        if (ormOr.getAndList().size() > 1)
            filterBuilder.append("(");

        for (int i = 0; i < ormOr.getAndList().size(); i++) {
            if (i > 0)
                filterBuilder.append(" AND ");
            buildAnd(queryInternalInfo, filterBuilder, optionBuilder, ormOr.getAndList().get(i));
        }

        if (ormOr.getAndList().size() > 1)
            filterBuilder.append(")");
    }

    private static void buildAnd(QueryInternalInfo queryInternalInfo, IndentStringBuilder filterBuilder, IndentStringBuilder optionBuilder, OrmAnd ormAnd) {
        if (ormAnd.getConditionList() == null || ormAnd.getConditionList().size() == 0)
            return;

        if (ormAnd.getConditionList().size() > 1)
            filterBuilder.append("( ");

        for (int i = 0; i < ormAnd.getConditionList().size(); i++) {
            if (i > 0)
                filterBuilder.append(" AND ");

            OrmCondition ormCondition = ormAnd.getConditionList().get(i);
            if (ormCondition instanceof OrmOr) {
                buildOr(queryInternalInfo, filterBuilder, optionBuilder, (OrmOr) ormCondition);
            } else if (ormCondition instanceof OrmAnd)
                buildAnd(queryInternalInfo, filterBuilder, optionBuilder, (OrmAnd) ormCondition);
            else if (ormCondition instanceof OrmOpExpression) {
                OrmOpExpression opExpression = (OrmOpExpression) ormCondition;
                filterBuilder.append(opExpression.toOpQL(queryInternalInfo));
            }
        }

        if (ormAnd.getConditionList().size() > 1)
            filterBuilder.append(") ");
    }
}