package com.shine.faas.domain.orm.entity;

import com.shine.faas.common.orm.BaseEntity;
import com.shine.faas.common.orm.annotation.Column;
import com.shine.faas.common.orm.annotation.Entity;
import com.shine.faas.common.orm.annotation.Table;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "admin")
public class Admin extends BaseEntity {
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name="birth")
    private Date birth;

    @Column(name="email")
    private String email;

    /**
     * 是否超级管理员
     */
    @Column(name = "is_super")
    private Boolean isSuper = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Boolean aSuper) {
        isSuper = aSuper;
    }
    //endregion
}
