package com.shine.faas.common.information;

import java.util.HashMap;
import java.util.Map;

public class JsonObject {

    private Map<String, Object> map = new HashMap<>();

    public JsonObject put(String key, Object val) {
        this.map.put(key, val);
        return this;
    }

    public Object get(String key) {
        return this.map.get(key);
    }


    public String getString(String key) {
        return (String) this.map.get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) this.map.get(key);
    }


    public Map<String, Object> toMap() {
        return this.map;
    }
}
