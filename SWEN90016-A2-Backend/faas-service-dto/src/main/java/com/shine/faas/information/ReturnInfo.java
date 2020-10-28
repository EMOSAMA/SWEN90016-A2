//package com.shine.faas.information;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//@JsonPropertyOrder({"success","message","data"})
//public class ReturnInfo<T> {
//
//    private Integer status = 0;
//
//    public ReturnInfo() {
//        this.success = true;
//    }
//
//    public ReturnInfo(T data)
//    {
//        this.success = true;
//        this.data = data;
//    }
//
//    public ReturnInfo(Boolean isSucess, T data)
//    {
//        this.status = isSucess ? 0 : 1;
//        this.success = isSucess;
//        this.data = data;
//    }
//    public ReturnInfo(Boolean isSucess, String message)
//    {
//        this.status = isSucess ? 0 : 1;
//        this.success = isSucess;
//        this.message = message;
//    }
//    public ReturnInfo(Boolean isSucess, T data, String message)
//    {
//        this.status = isSucess ? 0 : 1;
//        this.success = isSucess;
//        this.data = data;
//        this.message = message;
//    }
//    public ReturnInfo(int status, T data, String message)
//    {
//        this.status = status;
//        this.success = status == 0;
//        this.data = data;
//        this.message = message;
//    }
//
//    public Boolean isSuccess() {
//        return success;
//    }
//
//    @JsonIgnore
//    public Boolean isFail(){
//        return !success;
//    }
//
//    public void setSuccess(Boolean success) {
//        this.success = success;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    private  Boolean success;
//
//    private  String message;
//
//    private T data;
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//}
//
