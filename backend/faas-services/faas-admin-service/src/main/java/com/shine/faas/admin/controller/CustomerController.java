package com.shine.faas.admin.controller;

import com.shine.faas.admin.information.LoginReqInfo;
import com.shine.faas.admin.information.ModifyPwdInfo;
import com.shine.faas.admin.information.customer.CreateCustomerReqInfo;
import com.shine.faas.admin.information.customer.QueryCustomerReqInfo;
import com.shine.faas.admin.information.customer.UpdateCustomerReqInfo;
import com.shine.faas.admin.service.CustomerService;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.common.orm.OrmManager;
import com.shine.faas.common.util.ActionUtil;
import com.shine.faas.information.IdBundleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 使用 @RestController 定义一个控制器
 */
@RestController()
@RequestMapping(value = "")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 登录前台
     *
     * @param loginReqInfo
     * @return
     */
    @RequestMapping(value = "/customer/login", method = RequestMethod.POST)
    public ReturnInfo customerLogin(@RequestBody LoginReqInfo loginReqInfo) {

        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return customerService.customerLogin(context, loginReqInfo);
        });
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "/customer/password", method = RequestMethod.PUT)
    public ReturnInfo customerChangePassword(@RequestBody ModifyPwdInfo modifyPwdInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return customerService.changePassword(context, modifyPwdInfo);
        });
    }

    //管理员账户相关接口
    /**
     * 创建客户
     *
     * @param createCustomerReqInfo
     * @return
     */
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public ReturnInfo createCustomer(@RequestBody CreateCustomerReqInfo createCustomerReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return customerService.createCustomer(context, createCustomerReqInfo);
        });
    }

    /**
     * 更新客户
     *
     * @param updateCustomerReqInfo
     * @return
     */
    @RequestMapping(value = "/customer",method = RequestMethod.PUT)
    public ReturnInfo updateCustomer(@RequestBody UpdateCustomerReqInfo updateCustomerReqInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return customerService.updateCustomer(context,updateCustomerReqInfo);
        });
    }

    /**
     * 获取客户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public ReturnInfo getCustomer(@RequestParam("id") UUID id) {
        DbContext context = OrmManager.instance().createDbContext(true);
        return ActionUtil.doAction(context, () -> {
            return customerService.getCustomer(context, id);
        });
    }

    /**
     * 删除客户
     *
     * @param bundleInfo
     * @return
     */
    @RequestMapping(value = "/customer",method = RequestMethod.DELETE)
    public ReturnInfo deleteCustomer(@RequestBody IdBundleInfo bundleInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return customerService.deleteCustomer(context, bundleInfo.getIds());
        });
    }

    /**
     * 查询客户
     *
     * @param queryCustomerReqInfo
     * @return
     */
    @RequestMapping(value = "/customer/query", method = RequestMethod.POST)
    public ReturnInfo queryCustomer(@RequestBody QueryCustomerReqInfo queryCustomerReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return customerService.queryCustomer(context, queryCustomerReqInfo);
        });
    }
}
