package com.shine.faas.common.information;

import com.shine.faas.common.orm.AbstractEntity;
import com.shine.faas.common.orm.annotation.Column;

public class PairInfo extends AbstractEntity {
    @Column(name="key")
    private String key;
    @Column(name ="value")
    private Object value;

    //region get set

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    //endregion
}
