package com.shine.faas.common.orm.query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shine.faas.common.information.PrepareStatement;
import com.shine.faas.common.orm.QueryBuilder;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.orm.AbstractEntity;
import com.shine.faas.common.orm.FieldInfo;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by adam2 on 2019/5/7.
 * 针对单个类的实例查询生成的sparql，组合条件筛选，排序，允许分页
 */
public class OrmQueryAssitant {

    public static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }
//
//    private static final String buildSql(String baseSql, OrmQueryInfo ormQueryInfo, QueryInternalInfo queryInternalInfo) {
//
//    }
//
//    private static final String buildSql(String baseSql, QueryInternalInfo queryInternalInfo) {
//        //返回数据
//        IndentStringBuilder builder = new IndentStringBuilder();
//        builder.increaseIndentLine(baseSql);
//
//        String upperSql = baseSql.toUpperCase();
//        upperSql = upperSql.replaceAll("'.*'", "NULL");
//        if (queryInternalInfo.getFilter() != null && queryInternalInfo.getFilter().length() > 1) {
//            if (upperSql.lastIndexOf("WHERE") > upperSql.lastIndexOf("SELECT"))
//                builder.append(" AND ");
//            else
//                builder.append(" WHERE ");
//        }
//
//        if (!StringUtil.isEmpty(queryInternalInfo.getFilter()))
//            builder.append(queryInternalInfo.getFilter());
//
//        if (!StringUtil.isEmpty(queryInternalInfo.getOrderBy()))
//            builder.appendFormatLine(" ORDER BY " + queryInternalInfo.getOrderBy());
//
//        if (!StringUtil.isEmpty(queryInternalInfo.getPager()))
//            builder.appendFormatLine(queryInternalInfo.getPager());
//
//        return builder.getContent();
//    }

//    private static final String buildSqlForCount(String baseSql, QueryInternalInfo queryInternalInfo) {
//
//        //求总数
//        IndentStringBuilder builder = new IndentStringBuilder();
//        builder.increaseIndentLine("SELECT COUNT(1) AS count ");
//        builder.increaseIndentLine("FROM (");
//
//        builder.appendFormatLine(baseSql);
//
//        String upperSql = baseSql.toUpperCase();
//        upperSql = upperSql.replaceAll("'.*'", "NULL");
//
//        if (!StringUtil.isEmpty(queryInternalInfo.getFilter())) {
//
//            if (upperSql.lastIndexOf("WHERE") > upperSql.lastIndexOf("SELECT"))
//                builder.append(" AND ");
//            else
//                builder.append(" WHERE ");
//            builder.append(queryInternalInfo.getFilter());
//        }
//
//        builder.decreaseIndentLine(")T");
//
//        return builder.getContent();
//    }

    public static <T extends AbstractEntity> PagerResultInfo<T> queryPager(DbContext dbContext, String baseSql, OrmPagerOrmQueryInfo ormQueryInfo, Class<? extends AbstractEntity> entityClasss) throws Exception {

        if (dbContext == null)
            return null;

//        QueryInternalInfo queryInternalInfo = QueryBuilder.convert(ormQueryInfo);
        String sqlCount = QueryBuilder.buildCountSql(baseSql, ormQueryInfo);
        PagerResultInfo<T> pagerResultInfo = new PagerResultInfo<T>();
        pagerResultInfo.setPageIndex(ormQueryInfo.fillQueryInternalInfo().getPagerInfo().getPageIndex());
        pagerResultInfo.setPageSize(ormQueryInfo.fillQueryInternalInfo().getPagerInfo().getPageSize());

        PrepareStatement prepareStatement = new PrepareStatement();
        prepareStatement.fill(sqlCount, ormQueryInfo.fillQueryInternalInfo().getParameters());
        SqlRowSet resultSet = dbContext.getConnection().queryWithParams(prepareStatement.getSql(),
                prepareStatement.getJdbcParameters());

        Long count = 0L;
        String column = resultSet.getMetaData().getColumnNames()[0];
        try {
            if (resultSet.next()) {
                count = resultSet.getLong(column);
            }
        } catch (InvalidResultSetAccessException ex) {
            ex.printStackTrace();
        }

        pagerResultInfo.setTotalNumbers(count);
        pagerResultInfo.setPageIndex(ormQueryInfo.getPagerInfo().getPageIndex());
        pagerResultInfo.setPageSize(ormQueryInfo.getPagerInfo().getPageSize());

        if (count == 0)
            return pagerResultInfo;

        List<T> entityList = queryList(dbContext, baseSql, ormQueryInfo, entityClasss);
        pagerResultInfo.setResults(entityList);

        return pagerResultInfo;

    }


    public static <T extends AbstractEntity> T queryOne(DbContext dbContext, String baseSql, OrmQueryInfo ormQueryInfo, Class<? extends AbstractEntity> entityClass) throws Exception {
        List<T> entityList = queryList(dbContext, baseSql, ormQueryInfo, entityClass);
        if (entityList.size() > 0)
            return entityList.get(0);
        return null;
    }

    /**
     * 查询返回简单对象列表，返回类型只能是数字/字符串/uuid/日期等简单类型
     *
     * @param dbContext
     * @param baseSql
     * @param ormQueryInfo
     * @param entityClass
     * @param <T>
     * @return
     */

    public static <T> T querySimpleOne(DbContext dbContext, String baseSql, OrmQueryInfo ormQueryInfo, Class<?> entityClass) throws SQLException {
        List<T> rlst = querySimpleList(dbContext, baseSql, ormQueryInfo, entityClass);
        if (rlst.size() > 0)
            return rlst.get(0);
        return null;
    }

    /**
     * 查询返回简单对象列表，返回类型只能是数字/字符串/uuid/日期等简单类型
     *
     * @param dbContext
     * @param baseSql
     * @param ormQueryInfo
     * @param entityClass
     * @param <T>
     * @return
     */

    public static <T> List<T> querySimpleList(DbContext dbContext, String baseSql, OrmQueryInfo ormQueryInfo, Class<?> entityClass) throws SQLException {
        List<T> entityList = new ArrayList<>();
//        QueryInternalInfo queryInternalInfo = null;
//        if (ormQueryInfo instanceof OrmPagerOrmQueryInfo) {
//            queryInternalInfo = QueryBuilder.convert((OrmPagerOrmQueryInfo) ormQueryInfo);
//        } else
//            queryInternalInfo = QueryBuilder.convert(ormQueryInfo);
        String sql = QueryBuilder.buildSql(baseSql, ormQueryInfo);
        PrepareStatement prepareStatement = new PrepareStatement();

        prepareStatement.fill(sql, ormQueryInfo.fillQueryInternalInfo().getParameters());
        SqlRowSet resultSet = dbContext.getConnection().queryWithParams(prepareStatement.getSql(), prepareStatement.getJdbcParameters());

        try {
            String column = resultSet.getMetaData().getColumnNames()[0];
            while (resultSet.next()) {
                Object value = resultSet.getObject(column);
                if (value == null) {
                    entityList.add(null);
                    continue;
                }
                if (entityClass.equals(String.class))
                    entityList.add((T) value);
                else if (entityClass.isPrimitive())
                    entityList.add((T) value);
                else if (entityClass.equals(UUID.class)) {
                    entityList.add((T) UUID.fromString(value.toString()));
                } else if (entityClass.equals(BigDecimal.class)) {
                    entityList.add((T) BigDecimal.valueOf((Double) value));
                } else if (entityClass.equals(Long.class)) {
                    entityList.add((T) value);
                } else if (entityClass.equals(Integer.class)) {
                    entityList.add((T) value);
                } else if (entityClass.equals(BigInteger.class)) {
                    entityList.add((T) value);
                } else {
                    assert false;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return entityList;
    }


    public static <T extends AbstractEntity> List<T> queryList(DbContext dbContext, String baseSql, OrmQueryInfo ormQueryInfo, Class<? extends AbstractEntity> entityClass) throws Exception {

        List<T> entityList = new ArrayList<>();

//               QueryInternalInfo queryInternalInfo = null;
//        if (ormQueryInfo instanceof OrmPagerOrmQueryInfo) {
//            queryInternalInfo = QueryBuilder.convert((OrmPagerOrmQueryInfo) ormQueryInfo);
//        } else
//            queryInternalInfo = QueryBuilder.convert(ormQueryInfo);
//
//        ormQueryInfo.setQueryInternalInfo(queryInternalInfo);

        String sql = null;
        if (ormQueryInfo instanceof OrmPagerOrmQueryInfo) {
            sql = QueryBuilder.buildSql(baseSql, (OrmPagerOrmQueryInfo) ormQueryInfo);
        } else
            sql = QueryBuilder.buildSql(baseSql, ormQueryInfo);


        PrepareStatement prepareStatement = new PrepareStatement();

        prepareStatement.fill(sql, ormQueryInfo.fillQueryInternalInfo().getParameters());
        SqlRowSet resultSet = dbContext.getConnection().queryWithParams(prepareStatement.getSql(), prepareStatement.getJdbcParameters());
        T entity = createEntity(entityClass);
        List<FieldInfo> fieldInfos = entity.getFields(entity.getClass());

        try {
            while (resultSet.next()) {
                entity = createEntity(entityClass);
                for (int i = 0; i < fieldInfos.size(); i++) {
                    FieldInfo fieldInfo = fieldInfos.get(i);
                    String key = fieldInfo.getName();
                    fieldInfo.getSetMethod().invoke(entity, getColumnValue(fieldInfo, resultSet.getObject(key)));
                }
                entityList.add(entity);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        return entityList;
    }

    public static Integer executeUpdate(DbContext context, String sql) {
        return executeUpdate(context, sql, new OrmQueryInfo());
    }

    public static Integer executeUpdate(DbContext context, String baseSql, OrmQueryInfo ormQueryInfo) {
//        QueryInternalInfo queryInternalInfo = null;
//        if (ormQueryInfo instanceof OrmPagerOrmQueryInfo) {
//            queryInternalInfo = QueryBuilder.convert((OrmPagerOrmQueryInfo) ormQueryInfo);
//        } else
//            queryInternalInfo = QueryBuilder.convert(ormQueryInfo);
        String sql = QueryBuilder.buildSql(baseSql, ormQueryInfo);
        PrepareStatement prepareStatement = new PrepareStatement();

        prepareStatement.fill(sql, ormQueryInfo.fillQueryInternalInfo().getParameters());

        return context.getConnection().updateWithParams(prepareStatement.getSql(), prepareStatement.getJdbcParameters());
    }

    private static <T> T createEntity(Class<? extends AbstractEntity> entityClass) {
        try {
            Constructor constructor = entityClass.getConstructor();
            T t = (T) constructor.newInstance();
            return t;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Object getColumnValue(FieldInfo fieldInfo, Object val) {
        Class<?> type = fieldInfo.getField().getType();
        if (type.isPrimitive()) {
            return val == null ? null : val;
        } else if (type.equals(String.class))
            return val == null ? null : val;
        else if (type.equals(UUID.class))
            return val == null ? null : UUID.fromString(val.toString());
        else if (type.equals(Date.class)) {
            try {
                if (val == null)
                    return null;
                if (val instanceof Date)
                    return val;
                return dateFormat.parse(val.toString());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } else if (type.equals(Integer.class))
            return val == null ? null : val;
        else if (type.equals(Long.class))
            return val == null ? null : (Long) val;
        else if (type.equals(Boolean.class))
            return val == null ? null : (Boolean) val;
        else if (type.equals(BigDecimal.class))
            return val == null ? null : new BigDecimal(val.toString());
        else if (type.equals(JsonNode.class)) {
            try {
                return val == null ? null : new ObjectMapper().readTree(val.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (type.equals(Object.class))
            return val;
        return null;
    }
}
