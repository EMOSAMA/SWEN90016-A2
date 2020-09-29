package com.shine.faas.domain.orm.entity;

import com.shine.faas.common.orm.BaseEntity;
import com.shine.faas.common.orm.annotation.Column;
import com.shine.faas.common.orm.annotation.Entity;
import com.shine.faas.common.orm.annotation.Table;

import java.util.UUID;

@Entity
@Table(name = "biller")
public class Biller extends BaseEntity {
    @Column(name = "customer_id")
    UUID customerId;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
