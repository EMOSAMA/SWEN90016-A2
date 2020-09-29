package com.shine.faas.admin.information;

import java.util.UUID;

public class UserLoginResInfo {

    private String role;
    private UUID userId;

    //region get set


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    //endregion

}