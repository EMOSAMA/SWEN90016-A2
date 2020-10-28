package com.shine.faas.common.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shine.faas.common.util.GeneraterUtil;
import com.shine.faas.common.orm.annotation.Column;
import com.shine.faas.common.orm.annotation.Table;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.kb.study.GeneraterUtil;
//import com.kb.study.orm.annotation.Column;
//import com.kb.study.orm.annotation.Table;

public abstract class AbstractEntity implements Serializable {

    private static Map<Class, List<FieldInfo>> fieldListDict = new HashMap<>();

    @JsonIgnore
    public String getTable() {
        Table table = this.getClass().getAnnotation(Table.class);
        return table.name();
    }

    public synchronized static List<FieldInfo> getFields(Class<? extends AbstractEntity> entityClass) {
        try {

            List<Field> fieldList = new ArrayList<>();

            if (fieldListDict.containsKey(entityClass))
                return fieldListDict.get(entityClass);
            else {
                Class clazz = entityClass;
                while (clazz != null) {
                    Field[] fields = clazz.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        fieldList.add(fields[i]);
                    }
                    clazz = clazz.getSuperclass();
                }
            }

            List<FieldInfo> fieldInfos = new ArrayList<>();

            for (Field field : fieldList) {
                Annotation[] annotations = field.getAnnotationsByType(Column.class);
                if (annotations == null || annotations.length == 0)
                    continue;

                FieldInfo fieldInfo = new FieldInfo();
                Column column = (Column) annotations[0];
                fieldInfo.setName(column.name());
                fieldInfo.setField(field);
                fieldInfo.setLength(column.length());
                fieldInfo.setPrecision(column.precision());
                fieldInfo.setScale(column.scale());

                fieldInfo.setGetMethod(entityClass.getMethod("get" + GeneraterUtil.getClassName(field.getName())));
                for (Method method : entityClass.getMethods()) {
                    if (method.getName().equals("set" + GeneraterUtil.getClassName(field.getName()))) {
                        fieldInfo.setSetMethod(method);
                        break;
                    }
                }

                fieldInfos.add(fieldInfo);
            }
            fieldListDict.put(entityClass, fieldInfos);

            return fieldInfos;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
