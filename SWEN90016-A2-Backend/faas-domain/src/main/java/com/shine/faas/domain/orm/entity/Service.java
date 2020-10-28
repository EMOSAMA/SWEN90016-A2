package com.shine.faas.domain.orm.entity;

import com.shine.faas.common.orm.BaseEntity;
import com.shine.faas.common.orm.annotation.Column;
import com.shine.faas.common.orm.annotation.Entity;
import com.shine.faas.common.orm.annotation.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "service")
public class Service extends BaseEntity {
    @Column(name = "service_name")
    String serviceName;
    @Column(name = "cost")
    BigDecimal cost;
    @Column(name = "duration")
    BigDecimal duration;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }
}
