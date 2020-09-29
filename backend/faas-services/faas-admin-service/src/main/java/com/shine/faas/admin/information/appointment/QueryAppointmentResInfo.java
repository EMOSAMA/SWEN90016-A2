package com.shine.faas.admin.information.appointment;

import com.shine.faas.common.orm.AbstractEntity;
import com.shine.faas.common.orm.annotation.Column;
import java.util.Date;
import java.util.UUID;

public class QueryAppointmentResInfo extends AbstractEntity {
    @Column(name = "customer_id")
    UUID customerId;
    @Column(name = "service_id")
    UUID serviceId;
    @Column(name = "location")
    String location;
    @Column(name = "time")
    Date time;
    @Column(name = "status")
    String status;
    @Column(name = "message")
    String message;
    @Column(name = "name")
    String customerName;
    @Column(name = "service_name")
    String serviceName;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
