package com.shine.faas.admin.information.admin;

public class LoginReqInfo {

    private String userName;
    private String password;

    //region get set

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //endregion
}
