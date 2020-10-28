package com.shine.faas.common;


import com.shine.faas.common.information.ReturnInfo;

@FunctionalInterface
public interface ReqCustomer {
    ReturnInfo accept() throws Exception;
}
