package com.shine.faas.admin.information.user.admin;

import com.shine.faas.common.orm.query.PagerInfo;

public class QueryAdminReqInfo extends PagerInfo {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
