package com.shine.faas.admin.service;

import com.shine.faas.admin.information.customer.CreateCustomerReqInfo;
import com.shine.faas.admin.information.regist.RegistCustomerReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import org.springframework.stereotype.Component;

@Component
public interface RegistService {
    ReturnInfo registCustomer(DbContext context, RegistCustomerReqInfo registCreateCustomerReqInfo) throws Exception;
}
