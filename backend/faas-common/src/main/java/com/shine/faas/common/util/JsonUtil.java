package com.shine.faas.common.util;//package com.shine.faas.common.util;
//
//import com.fasterxml.jackson.databind.JsonNode;
//
//import java.util.List;
//
//public class JsonUtil {
//
//    public static JsonObject toJson(Object object) {
//        if (object == null) return null;
//        if (object instanceof List) {
//            return new JsonObject("{ \"data\":" + new Gson().toJson(object) + "}");
//        }
//        return new JsonObject(new Gson().toJson(object));
//    }
//
//    public static JsonObject toJson(JsonNode object) {
//        if (object == null) return null;
////        if (object instanceof List) {
////            return new JsonObject("{ \"data\":" + new Gson().toJson(object) + "}");
////        }
//        return new JsonObject(object.toString());
//    }
//}
