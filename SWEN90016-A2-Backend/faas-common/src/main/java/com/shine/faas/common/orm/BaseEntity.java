package com.shine.faas.common.orm;

import com.shine.faas.common.orm.annotation.Id;
import com.shine.faas.common.orm.annotation.Column;

import java.util.Date;
import java.util.UUID;

public class BaseEntity extends AbstractEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    protected UUID id;

    @Column(updatable = false, name = "create_time")
    protected Date createTime = new Date();

    @Column(updatable = false, name = "create_user")
    protected UUID createUser;

    @Column(insertable = false, name = "update_time")
    protected Date updateTime = null;

    @Column(insertable = false, name = "update_user")
    protected UUID updateUser;

    @Column(name = "recycled")
    protected Boolean recycled = false;

    public BaseEntity() {
        this.updateTime = this.createTime = new Date();
    }

    //get,set方法

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        //保证更新时间不为空且不能早于创建时间
        if (this.updateTime == null || this.updateTime.before(this.createTime)) {
            this.updateTime = this.createTime;
        }
    }

    public UUID getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UUID createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public UUID getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(UUID updateUser) {
        this.updateUser = updateUser;
    }

    public Boolean getRecycled() {
        return recycled;
    }

    public void setRecycled(Boolean recycled) {
        this.recycled = recycled;
    }
}