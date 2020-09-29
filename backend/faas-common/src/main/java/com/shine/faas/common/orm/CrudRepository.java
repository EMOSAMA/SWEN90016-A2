package com.shine.faas.common.orm;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shine.faas.common.util.IndentStringBuilder;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.JsonObject;
import com.shine.faas.common.orm.annotation.Table;
import com.shine.faas.common.orm.query.OrmQueryAssitant;
import com.shine.faas.common.orm.query.OrmQueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudRepository<T extends BaseEntity, ID extends Serializable> {

    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate;

    public String getTable() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class<?> entityClass = (Class) params[0];
        Table table = entityClass.getAnnotation(Table.class);
        return table.name();
    }

    private <T extends AbstractEntity> T createEntity() {
        try {
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            Class<?> entityClass = (Class) params[0];
            Constructor constructor = entityClass.getConstructor();
            T t = (T) constructor.newInstance();
            return t;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void insert(DbContext context, T entity) throws Exception {
        List<T> entities = new ArrayList<>();
        entities.add(entity);
        insert(context, entities);
    }

    public void insert(DbContext context, List<T> entities) throws Exception {
        if (entities == null || entities.size() == 0)
            return;

        T firstEntity = entities.get(0);
        List<FieldInfo> fieldInfos = firstEntity.getFields(firstEntity.getClass());
        List<JsonObject> jsonArrays = new ArrayList<>();
        IndentStringBuilder builder = new IndentStringBuilder();

        builder.appendFormatLine("INSERT INTO {0} (", firstEntity.getTable());
        for (int i = 0; i < fieldInfos.size(); i++) {
            if (i > 0)
                builder.append("," + fieldInfos.get(i).getName() + "");
            else
                builder.append("" + fieldInfos.get(i).getName() + "");
        }

        builder.appendFormatLine(") VALUES (");
        for (int i = 0; i < fieldInfos.size(); i++) {
            if (i > 0) {
                builder.append(",:" + fieldInfos.get(i).getName());
            } else {
                builder.append(":" + fieldInfos.get(i).getName());
            }
            Class<?> type = fieldInfos.get(i).getField().getType();
            if (type.equals(ObjectNode.class)) {
                builder.append("::JSONB");
            }
        }
        builder.appendFormatLine(");");

        for (T entity : entities) {
            JsonObject params = new JsonObject();
            for (int i = 0; i < fieldInfos.size(); i++) {
                getFieldValue(params, entity, fieldInfos.get(i));
            }
            jsonArrays.add(params);
        }

        context.getConnection().batchWithParams(builder.getContent(), jsonArrays);
    }

    public void update(DbContext context, T entity) throws Exception {

        List<T> entities = new ArrayList<>();
        entities.add(entity);
        update(context, entities);
    }

    public void update(DbContext context, List<T> entities) throws Exception {
        if (entities == null || entities.size() == 0)
            return;

        T firstEntity = entities.get(0);
        List<FieldInfo> fieldInfos = firstEntity.getFields(firstEntity.getClass());
        List<JsonObject> jsonArrays = new ArrayList<>();
        IndentStringBuilder builder = new IndentStringBuilder();
        builder.appendFormatLine("UPDATE {0} SET ", firstEntity.getTable());
//        JsonArray params = new JsonArray();

        for (int i = 0; i < fieldInfos.size(); i++) {
            FieldInfo fieldInfo = fieldInfos.get(i);
            if (fieldInfo.getName().toLowerCase().equals("id")) {
                continue;
            }
            if (i > 0)
                builder.append("," + fieldInfo.getName() + "= :" + fieldInfos.get(i).getName());// + getFieldValue(entity, fieldInfos.get(i)));
            else
                builder.append("" + fieldInfo.getName() + "= :" + fieldInfos.get(i).getName());// + getFieldValue(entity, fieldInfos.get(i)));

            Class<?> type = fieldInfos.get(i).getField().getType();
            if (type.equals(ObjectNode.class)) {
                builder.append("::JSONB");
            }
        }

        builder.append(" WHERE id = :id");
//        UpdateResult updateResult = awaitResult(h -> context.getConnection().updateWithParams(builder.getContent(), params, h));
        for (T entity : entities) {
            JsonObject params = new JsonObject();
            FieldInfo idField = null;
            for (int i = 0; i < fieldInfos.size(); i++) {
                FieldInfo fieldInfo = fieldInfos.get(i);
                if (fieldInfo.getName().toLowerCase().equals("id")) {
                    idField = fieldInfo;
                    continue;
                }
                getFieldValue(params, entity, fieldInfo);
            }

            if (idField != null)
                getFieldValue(params, entity, idField);
            jsonArrays.add(params);
        }
        context.getConnection().batchWithParams(builder.getContent(), jsonArrays);

    }

    private void getFieldValue(JsonObject paras, T entity, FieldInfo fieldInfo) throws Exception {
        Class<?> type = fieldInfo.getField().getType();
        Object obj = fieldInfo.getGetMethod().invoke(entity, null);
        paras.put(fieldInfo.getName(), obj);
//        if (type.isPrimitive()) {
//            if (obj == null) paras.addNull();
//            else assert false;
//        } else if (type.equals(String.class))
//            if (obj == null) paras.addNull();
//            else paras.add(obj.toString());
//        else if (type.equals(UUID.class))
//            if (obj == null) paras.addNull();
//            else paras.add(obj.toString());
//        else if (type.equals(Boolean.class))
//            if (obj == null) paras.addNull();
//            else paras.add((Boolean) obj);
//        else if (type.equals(Long.class))
//            if (obj == null) paras.addNull();
//            else paras.add((Long) obj);
//        else if (type.equals(Integer.class))
//            if (obj == null) paras.addNull();
//            else paras.add((Integer) obj);
//        else if (type.equals(Short.class))
//            if (obj == null) paras.addNull();
//            else paras.add((Short) obj);
//        else if (type.equals(Date.class))
//            if (obj == null) paras.addNull();
//            else paras.add(((Date) obj).getTime());
//        else if (type.equals(BigDecimal.class))
//            if (obj == null) paras.addNull();
//            else paras.add((BigDecimal) obj);
//        else if (type.equals(ObjectNode.class)) {
//            if (obj == null) paras.addNull();
//            else paras.add(obj.toString());
//        } else {
//            System.out.println("不能获取值");
//        }
    }

    public T findOne(DbContext context, ID id) throws Exception {
        IndentStringBuilder builder = new IndentStringBuilder();
        builder.appendFormatLine("SELECT ");
        T entity = createEntity();
        List<FieldInfo> fieldInfos = entity.getFields(entity.getClass());
        for (int i = 0; i < fieldInfos.size(); i++) {

            FieldInfo fieldInfo = fieldInfos.get(i);
            if (i == 0)
                builder.append(fieldInfo.getName());
            else
                builder.append("," + fieldInfo.getName());
        }

        builder.appendFormatLine(" FROM {0} WHERE id = :id", this.getTable());
        OrmQueryInfo queryInfo = new OrmQueryInfo();
        queryInfo.getParameters().put("id", id);
        return OrmQueryAssitant.queryOne(context, builder.getContent(), queryInfo, this.createEntity().getClass());
    }

//    @org.springframework.data.annotation.Query(name = "findAll", value = "select * from legal_currency where recycled = false ")
//    public List<T> findAll(DbContext context) {
//        String sql = "select * from " + this.getTable() + " where recycled = false";
//        OrmQueryInfo queryInfo = new OrmQueryInfo();
//        return OrmQueryAssitant.queryList(context, sql, queryInfo, createEntity().getClass());
//    }

    public List<T> findAll(DbContext context, List<ID> ids) {
        if (ids == null || ids.size() == 0)
            return new ArrayList<>();

        try {
            IndentStringBuilder builder = new IndentStringBuilder();
            builder.append("SELECT ");
            T entity = createEntity();
            List<FieldInfo> fieldInfos = entity.getFields(entity.getClass());
            for (int i = 0; i < fieldInfos.size(); i++) {

                FieldInfo fieldInfo = fieldInfos.get(i);
                if (i == 0)
                    builder.append(fieldInfo.getName());
                else
                    builder.append("," + fieldInfo.getName());
            }

            builder.appendFormatLine(" FROM {0} WHERE id in (:ids)", this.getTable());
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.put("ids", ids);
//
//            PrepareStatement prepareStatement = new PrepareStatement();
//            prepareStatement.fill(builder.getContent(), jsonObject);
            //ResultSet resultSet = awaitResult(h -> context.getConnection().queryWithParams(prepareStatement.getSql(), prepareStatement.getJdbcParameters(), h));
//            ResultSet resultSet = awaitResult(h -> context.getConnection().query(prepareStatement.getSql(), h));
//            System.out.println(111);
            OrmQueryInfo queryInfo = new OrmQueryInfo();
            queryInfo.getParameters().put("ids", ids);
            return OrmQueryAssitant.queryList(context, builder.getContent(), queryInfo, this.createEntity().getClass());
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }

//        return null;
    }

    public void delete(DbContext context, ID id) {
        IndentStringBuilder builder = new IndentStringBuilder();
        builder.appendFormatLine("DELETE  ");
        builder.appendFormatLine(" FROM {0} WHERE id = '" + id + "'", this.getTable());
        context.getConnection().update(builder.getContent());
    }
}
