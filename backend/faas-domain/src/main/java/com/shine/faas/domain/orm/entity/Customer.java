package com.shine.faas.domain.orm.entity;

import com.shine.faas.common.orm.BaseEntity;
import com.shine.faas.common.orm.annotation.Column;
import com.shine.faas.common.orm.annotation.Entity;
import com.shine.faas.common.orm.annotation.Table;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {
    @Column(name = "name")
    String name;
    @Column(name = "address")
    String address;
    @Column(name = "phone")
    String phone;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;
    @Column(name = "comment")
    String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}