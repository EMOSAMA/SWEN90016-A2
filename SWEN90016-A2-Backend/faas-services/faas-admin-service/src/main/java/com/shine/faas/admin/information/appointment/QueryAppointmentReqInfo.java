package com.shine.faas.admin.information.appointment;

import com.shine.faas.common.orm.query.PagerInfo;

import java.util.Date;
import java.util.UUID;

public class QueryAppointmentReqInfo extends PagerInfo {
    public UUID serviceId;
    public UUID customerId;
    public String customerName;
    public Date date;
    public Integer time;
    public String status;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
