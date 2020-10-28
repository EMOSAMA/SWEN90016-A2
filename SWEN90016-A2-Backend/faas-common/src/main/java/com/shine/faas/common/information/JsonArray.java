package com.shine.faas.common.information;

        import java.util.ArrayList;
        import java.util.List;

public class JsonArray {

    private List<Object> objects = new ArrayList<>();

    public JsonArray() {

    }

    public JsonArray(List<Object> objects) {
        this.objects.addAll(objects);
    }

    public void add(Object obj) {
        this.objects.add(obj);
    }

    public Integer size() {
        return this.objects.size();
    }

    public Object get(int index) {
        return this.objects.get(index);
    }


}
