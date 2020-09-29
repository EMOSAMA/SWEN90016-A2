package com.shine.faas.admin.information.service;

import com.shine.faas.common.orm.query.PagerInfo;

public class QueryServiceReqInfo extends PagerInfo{
    public String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
