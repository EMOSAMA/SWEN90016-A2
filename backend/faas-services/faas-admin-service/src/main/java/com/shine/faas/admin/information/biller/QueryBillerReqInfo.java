package com.shine.faas.admin.information.biller;

import com.shine.faas.common.orm.query.PagerInfo;

import java.util.UUID;

public class QueryBillerReqInfo extends PagerInfo {
    public UUID customerId;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
}
