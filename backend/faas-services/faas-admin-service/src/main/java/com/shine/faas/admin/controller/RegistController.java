package com.shine.faas.admin.controller;

import com.shine.faas.admin.information.customer.CreateCustomerReqInfo;
import com.shine.faas.admin.information.regist.RegistCustomerReqInfo;
import com.shine.faas.admin.service.CustomerService;
import com.shine.faas.admin.service.RegistService;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.common.orm.OrmManager;
import com.shine.faas.common.util.ActionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "")
public class RegistController {
    @Autowired
    private RegistService registService;

    /**
     * Regist
     *
     * @param registCustomerReqInfo
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public ReturnInfo registCustomer(@RequestBody RegistCustomerReqInfo registCustomerReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return registService.registCustomer(context, registCustomerReqInfo);
        });
    }
}
