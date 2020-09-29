package com.shine.faas.admin.service.impl;

import com.shine.faas.admin.information.LoginReqInfo;
import com.shine.faas.admin.information.LoginResInfo;
import com.shine.faas.admin.information.ModifyPwdInfo;
import com.shine.faas.admin.information.customer.CreateCustomerReqInfo;
import com.shine.faas.admin.information.customer.QueryCustomerReqInfo;
import com.shine.faas.admin.information.customer.UpdateCustomerReqInfo;
import com.shine.faas.admin.mapper.MapFactory;
import com.shine.faas.admin.orm.RepositoryFactory;
import com.shine.faas.admin.service.CustomerService;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.common.orm.query.PagerResultInfo;
import com.shine.faas.domain.orm.entity.Admin;
import com.shine.faas.domain.orm.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RepositoryFactory repositoryFactory;

    @Autowired
    private MapFactory mapFactory;

    @Override
    public ReturnInfo customerLogin(DbContext context, LoginReqInfo loginReqInfo) throws Exception {

        if (loginReqInfo.getUserName() == null) {
            return new ReturnInfo(false, "用户名不能为空");
        }
        if (loginReqInfo.getPassword() == null) {
            return new ReturnInfo(false, "密码不能为空");
        }

        Admin adminUser = repositoryFactory.adminRepository.getAuthorizeUser(context, loginReqInfo.getUserName(), loginReqInfo.getPassword());
        if (adminUser == null || adminUser.getRecycled())
            return new ReturnInfo(false, "用户名或者密码出错");

        LoginResInfo resInfo = new LoginResInfo();
        resInfo.setUserId(adminUser.getId());
        return new ReturnInfo(resInfo);
    }

    @Override
    public ReturnInfo changePassword(DbContext context, ModifyPwdInfo modifyPwdInfo) throws Exception {

        if (modifyPwdInfo.getUserId() == null)
            return new ReturnInfo(false, "必须指定用户");

        if (modifyPwdInfo.getOldPassword() == null)
            return new ReturnInfo(false, "必须传入旧密码");

        if (modifyPwdInfo.getPassword() == null)
            return new ReturnInfo(false, "密码必须非空");

        if (modifyPwdInfo.getPassword().length() < 6)
            return new ReturnInfo(false, "密码长度不能小于6位");

        if (modifyPwdInfo.getPassword().length() > 12)
            return new ReturnInfo(false, "密码长度不能大于12位");

        Customer user = this.repositoryFactory.customerRepository.findOne(context, modifyPwdInfo.getUserId());
        if (user == null || user.getRecycled())
            return new ReturnInfo(false, "用户不存在");


        if (!user.getPassword().equals(modifyPwdInfo.getOldPassword()))
            return new ReturnInfo(false, "输入的旧密码错误");

        user.setPassword(modifyPwdInfo.getPassword());
        user.setUpdateTime(new Date());
        this.repositoryFactory.customerRepository.update(context, user);

        return new ReturnInfo();
    }

    //管理员相关服务重载
    @Override
    public ReturnInfo createCustomer(DbContext context, CreateCustomerReqInfo createCustomerReqInfo) throws Exception{
        Customer customer=this.mapFactory.customerMapper.map(createCustomerReqInfo);
        customer.setId(UUID.randomUUID());
        customer.setCreateTime(new Date());
        this.repositoryFactory.customerRepository.insert(context,customer);

        return (new ReturnInfo(customer.getId()));
    }

    @Override
    public ReturnInfo updateCustomer(DbContext context, UpdateCustomerReqInfo updateCustomerReqInfo) throws Exception{
        Customer oldCustomer=this.repositoryFactory.customerRepository.findOne(context, updateCustomerReqInfo.getId());
        if(oldCustomer==null){
            return (new ReturnInfo(false,"该管理员不存在"));
        }
        Customer customer=this.mapFactory.customerMapper.map(updateCustomerReqInfo);
        customer.setCreateTime(oldCustomer.getCreateTime());
        customer.setUpdateTime(new Date());

        this.repositoryFactory.customerRepository.update(context,customer);
        return new ReturnInfo(true,"更新成功");

    }

    @Override
    public ReturnInfo getCustomer(DbContext context, UUID id) throws Exception{
        Customer customer=this.repositoryFactory.customerRepository.findOne(context,id);
        return new ReturnInfo(context);
    }

    @Override
    public ReturnInfo deleteCustomer(DbContext context, List<UUID> ids) throws Exception{
        List<Customer> customers= this.repositoryFactory.customerRepository.findAll(context,ids);
        customers.forEach(customer -> {
            customer.setRecycled(true);
            customer.setUpdateTime(new Date());
        });
        this.repositoryFactory.customerRepository.update(context,customers);
        return new ReturnInfo(true,"删除成功");
    }

    @Override
    public ReturnInfo queryCustomer(DbContext context, QueryCustomerReqInfo queryCustomerReqInfo) throws Exception{
        PagerResultInfo<Customer> resultInfo =this.repositoryFactory.customerRepository.queryCustomer(context,queryCustomerReqInfo);
        return new ReturnInfo(resultInfo);
    }
}
