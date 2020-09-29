package com.shine.faas.common.orm;

import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class QueryHelper {

    private static Map<Class, Map<String, String>> queryMap = new HashMap<>();

    public static String getQuery(Class clazz, String queryName) {
        if (queryMap.containsKey(clazz)) {
            if (queryMap.get(clazz).containsKey(queryName))
                return queryMap.get(clazz).get(queryName);
            return null;
        }

        Map<String, String> stringMap = new HashMap<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            Query query = method.getAnnotation(Query.class);
            if (query == null)
                continue;

            stringMap.put(query.name(), query.value());
        }
        queryMap.put(clazz, stringMap);

        if (queryMap.containsKey(clazz)) {
            if (queryMap.get(clazz).containsKey(queryName))
                return queryMap.get(clazz).get(queryName);

        }
        return null;
    }
}
