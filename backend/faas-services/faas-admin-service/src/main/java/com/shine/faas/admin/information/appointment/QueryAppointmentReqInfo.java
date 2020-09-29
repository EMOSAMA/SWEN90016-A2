package com.shine.faas.admin.information.appointment;

import com.shine.faas.common.orm.query.PagerInfo;

import java.util.UUID;

public class QueryAppointmentInfo extends PagerInfo {
    public UUID serviceId;
    public String customerName;
    public String status;

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
