package com.shine.faas.domain.orm.entity;

import com.shine.faas.common.orm.BaseEntity;
import com.shine.faas.common.orm.annotation.Column;
import com.shine.faas.common.orm.annotation.Entity;
import com.shine.faas.common.orm.annotation.Table;

@Entity
@Table(name = "service")
public class Service extends BaseEntity {
    @Column(name = "service_name")
    String serviceNanme;
    @Column(name = "cost")
    Float cost;
    @Column(name = "duration")
    Float duration;

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
