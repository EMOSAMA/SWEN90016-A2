package com.shine.faas.admin.service.impl;

import com.shine.faas.admin.information.LoginResInfo;
import com.shine.faas.admin.information.regist.RegistCustomerReqInfo;
import com.shine.faas.admin.orm.RepositoryFactory;
import com.shine.faas.admin.service.RegistService;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.domain.orm.entity.Biller;
import com.shine.faas.domain.orm.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RegistServiceImpl implements RegistService {
    @Autowired
    private RepositoryFactory repositoryFactory;
    public ReturnInfo registCustomer(DbContext context, RegistCustomerReqInfo registCreateCustomerReqInfo) throws Exception {
        Customer customer=new Customer();
        customer.setAddress(registCreateCustomerReqInfo.getAddress());
        customer.setComment(registCreateCustomerReqInfo.getComment());
        customer.setEmail(registCreateCustomerReqInfo.getEmail());
        customer.setName(registCreateCustomerReqInfo.getName());
        customer.setPassword(registCreateCustomerReqInfo.getPassword());
        customer.setPhone(registCreateCustomerReqInfo.getPhone());
        customer.setId(UUID.randomUUID());
        customer.setCreateTime(new Date());

        Biller biller=new Biller();
        biller.setCustomerId(customer.getId());
        biller.setName(registCreateCustomerReqInfo.getBillerName());
        biller.setEmail(registCreateCustomerReqInfo.getBillerEmail());

        this.repositoryFactory.customerRepository.insert(context,customer);
        this.repositoryFactory.billerRepository.insert(context,biller);

        LoginResInfo resInfo = new LoginResInfo();
        resInfo.setUserId(customer.getId());
        return new ReturnInfo(resInfo);
    }

}
