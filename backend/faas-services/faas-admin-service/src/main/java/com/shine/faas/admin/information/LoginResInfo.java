package com.shine.faas.admin.information.admin;

import java.util.UUID;

public class LoginResInfo {

    private Boolean isSuper;
    private UUID userId;

    //region get set

    public Boolean getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Boolean isSuper) {
        this.isSuper = isSuper;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    //endregion

}
