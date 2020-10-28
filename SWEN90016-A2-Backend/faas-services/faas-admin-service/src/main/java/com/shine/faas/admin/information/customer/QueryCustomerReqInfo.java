package com.shine.faas.admin.information.customer;

import com.shine.faas.common.orm.query.PagerInfo;

public class QueryCustomerReqInfo extends PagerInfo {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
