package com.shine.faas.admin.information.service;

import java.util.UUID;

public class ServiceInfo {
    UUID id;
    String serviceNanme;
    Float cost;
    Float duration;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getServiceNanme() {
        return serviceNanme;
    }

    public void setServiceNanme(String serviceNanme) {
        this.serviceNanme = serviceNanme;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }
}
