package com.shine.faas.admin.service;

import com.shine.faas.admin.information.LoginReqInfo;
import com.shine.faas.admin.information.ModifyPwdInfo;
import com.shine.faas.admin.information.admin.CreateAdminReqInfo;
import com.shine.faas.admin.information.admin.QueryAdminReqInfo;
import com.shine.faas.admin.information.admin.UpdateAdminReqInfo;
import com.shine.faas.admin.information.customer.CreateCustomerReqInfo;
import com.shine.faas.admin.information.customer.QueryCustomerReqInfo;
import com.shine.faas.admin.information.customer.UpdateCustomerReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface CustomerService {
    ReturnInfo customerLogin(DbContext context, LoginReqInfo loginReqInfo) throws Exception;

    ReturnInfo changePassword(DbContext context, ModifyPwdInfo modifyPwdInfo) throws Exception;

    ReturnInfo createCustomer(DbContext context, CreateCustomerReqInfo createCustomerReqInfo) throws Exception;

    ReturnInfo updateCustomer(DbContext context, UpdateCustomerReqInfo updateCustomerReqInfo) throws Exception;

    ReturnInfo getCustomer(DbContext context, UUID id) throws Exception;

    ReturnInfo deleteCustomer(DbContext context, List<UUID> ids) throws Exception;

    ReturnInfo queryCustomer(DbContext context, QueryCustomerReqInfo queryCustomerReqInfo) throws Exception;
}
