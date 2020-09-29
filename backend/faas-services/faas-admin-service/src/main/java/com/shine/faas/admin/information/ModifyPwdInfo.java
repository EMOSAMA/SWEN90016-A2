package com.shine.faas.admin.information;

import java.util.UUID;

public class ModifyPwdInfo {
    private UUID userId;
    private String oldPassword;
    private String password;

    //region get set

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //endregion
}
