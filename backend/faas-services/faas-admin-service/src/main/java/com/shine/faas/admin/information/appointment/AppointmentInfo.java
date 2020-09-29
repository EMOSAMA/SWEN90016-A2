package com.shine.faas.admin.information.appointment;

import java.util.Date;
import java.util.UUID;

public class AppointmentInfo {
    UUID id;
    UUID customer_id;
    UUID service_id;
    Date time;
    String location;
    String status;
    String message;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(UUID customer_id) {
        this.customer_id = customer_id;
    }

    public UUID getService_id() {
        return service_id;
    }

    public void setService_id(UUID service_id) {
        this.service_id = service_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
}
