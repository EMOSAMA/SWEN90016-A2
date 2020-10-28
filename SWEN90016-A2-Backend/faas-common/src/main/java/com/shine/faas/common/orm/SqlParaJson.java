package com.shine.faas.common.orm;

import com.shine.faas.common.information.JsonObject;

public class SqlParaJson extends JsonObject {

//    private JsonObject jsonObject = new JsonObject();

//    private JsonObject base;

    public SqlParaJson() {


    }

    public JsonObject put(String key, Object value) {
//        if (value instanceof UUID)
//            super.put(key, value.toString());
//        else if (value instanceof Date)
//            super.put(key, ((Date) value).getTime());
//        else {
//            if (base.isBinary())
//                base.put(key, (byte[]) value);
//            else if (base.isBigDecimal())
//                base.put(key, (BigDecimal) value);
//            else if (base.isLong())
//                base.put(key, (Long) value);
//            else if (base.isBigInteger())
//                base.put(key, (Long) value);
//            else if (base.isBoolean())
//                base.put(key, (Boolean) value);
//            else if (base.isDouble())
//                base.put(key, (Double) value);
//            else if (base.isFloat())
//                base.put(key, (Float) value);
//            else if (base.isObject())
//                base.put(key, (ObjectNode) value);
//            else if (base.isInt())
//                base.put(key, (Integer) value);
//            else if (base.isShort())
//                base.put(key, (Short) value);
//            else if (base.isNull())
//                base.put(key, (Object)null);
            super.put(key,value);
//        }
        return this;
    }
}