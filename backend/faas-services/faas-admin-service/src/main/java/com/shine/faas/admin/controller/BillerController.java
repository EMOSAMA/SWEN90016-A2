package com.shine.faas.admin.controller;

import com.shine.faas.admin.information.appointment.CreateAppointmentReqInfo;
import com.shine.faas.admin.information.appointment.QueryAppointmentReqInfo;
import com.shine.faas.admin.information.appointment.UpdateAppointmentReqInfo;
import com.shine.faas.admin.information.biller.CreateBillerReqInfo;
import com.shine.faas.admin.information.biller.QueryBillerReqInfo;
import com.shine.faas.admin.information.biller.UpdateBillerReqInfo;
import com.shine.faas.admin.service.AppointmentService;
import com.shine.faas.admin.service.BillerService;
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
public class BillerController {
    @Autowired
    private BillerService billerService;
    //管理员账户相关接口
    /**
     * 创建服务
     *
     * @param createBillerReqInfo
     * @return
     */
    @RequestMapping(value = "/biller", method = RequestMethod.POST)
    public ReturnInfo createBiller(@RequestBody CreateBillerReqInfo createBillerReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return billerService.createBiller(context, createBillerReqInfo);
        });
    }

    /**
     * 更新服务
     *
     * @param updateBillerReqInfo
     * @return
     */
    @RequestMapping(value = "/biller",method = RequestMethod.PUT)
    public ReturnInfo updateBiller(@RequestBody UpdateBillerReqInfo updateBillerReqInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return billerService.updateBiller(context,updateBillerReqInfo);
        });
    }

    /**
     * 获取服务
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/biller", method = RequestMethod.GET)
    public ReturnInfo getBiller(@RequestParam("id") UUID id) {
        DbContext context = OrmManager.instance().createDbContext(true);
        return ActionUtil.doAction(context, () -> {
            return billerService.getBiller(context, id);
        });
    }

    /**
     * 删除服务
     *
     * @param bundleInfo
     * @return
     */
    @RequestMapping(value = "/biller",method = RequestMethod.DELETE)
    public ReturnInfo deleteBiller(@RequestBody IdBundleInfo bundleInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return billerService.deleteBiller(context, bundleInfo.getIds());
        });
    }

    /**
     * 查询服务
     *
     * @param queryBillerReqInfo
     * @return
     */
    @RequestMapping(value = "/biller/query", method = RequestMethod.POST)
    public ReturnInfo queryBiller(@RequestBody QueryBillerReqInfo queryBillerReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return billerService.queryBiller(context, queryBillerReqInfo);
        });
    }
}
