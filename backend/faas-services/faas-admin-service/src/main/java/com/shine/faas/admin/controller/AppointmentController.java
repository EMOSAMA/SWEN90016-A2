package com.shine.faas.admin.controller;

import com.shine.faas.admin.information.appointment.CreateAppointmentReqInfo;
import com.shine.faas.admin.information.appointment.QueryAppointmentReqInfo;
import com.shine.faas.admin.information.appointment.UpdateAppointmentReqInfo;
import com.shine.faas.admin.information.service.CreateServiceReqInfo;
import com.shine.faas.admin.information.service.QueryServiceReqInfo;
import com.shine.faas.admin.information.service.UpdateServiceReqInfo;
import com.shine.faas.admin.service.AppointmentService;
import com.shine.faas.admin.service.ServiceService;
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
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    //管理员账户相关接口
    /**
     * 创建服务
     *
     * @param createAppointmentReqInfo
     * @return
     */
    @RequestMapping(value = "/appointment", method = RequestMethod.POST)
    public ReturnInfo createAppointment(@RequestBody CreateAppointmentReqInfo createAppointmentReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return appointmentService.createAppointment(context, createAppointmentReqInfo);
        });
    }

    /**
     * 更新服务
     *
     * @param updateAppointmentReqInfo
     * @return
     */
    @RequestMapping(value = "/appointment",method = RequestMethod.PUT)
    public ReturnInfo updateAppointment(@RequestBody UpdateAppointmentReqInfo updateAppointmentReqInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return appointmentService.updateAppointment(context,updateAppointmentReqInfo);
        });
    }

    /**
     * 获取服务
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/appointment", method = RequestMethod.GET)
    public ReturnInfo getAppointment(@RequestParam("id") UUID id) {
        DbContext context = OrmManager.instance().createDbContext(true);
        return ActionUtil.doAction(context, () -> {
            return appointmentService.getAppointment(context, id);
        });
    }

    /**
     * 删除服务
     *
     * @param bundleInfo
     * @return
     */
    @RequestMapping(value = "/appointment",method = RequestMethod.DELETE)
    public ReturnInfo deleteAppointment(@RequestBody IdBundleInfo bundleInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return appointmentService.deleteAppointment(context, bundleInfo.getIds());
        });
    }

    /**
     * 查询服务
     *
     * @param queryAppointmentReqInfo
     * @return
     */
    @RequestMapping(value = "/appointment/query", method = RequestMethod.POST)
    public ReturnInfo queryAppointment(@RequestBody QueryAppointmentReqInfo queryAppointmentReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return appointmentService.queryAppointment(context, queryAppointmentReqInfo);
        });
    }
}
