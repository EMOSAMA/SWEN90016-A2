package com.shine.faas.admin.controller;
import com.shine.faas.admin.information.service.CreateServiceReqInfo;
import com.shine.faas.admin.information.service.QueryServiceReqInfo;
import com.shine.faas.admin.information.service.UpdateServiceReqInfo;
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
public class ServiceController {
    @Autowired
    private ServiceService serviceService;
    //管理员账户相关接口
    /**
     * 创建服务
     *
     * @param createServiceReqInfo
     * @return
     */
    @RequestMapping(value = "/service", method = RequestMethod.POST)
    public ReturnInfo createService(@RequestBody CreateServiceReqInfo createServiceReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return serviceService.createService(context, createServiceReqInfo);
        });
    }

    /**
     * 更新服务
     *
     * @param updateServiceReqInfo
     * @return
     */
    @RequestMapping(value = "/service",method = RequestMethod.PUT)
    public ReturnInfo updateService(@RequestBody UpdateServiceReqInfo updateServiceReqInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return serviceService.updateService(context,updateServiceReqInfo);
        });
    }

    /**
     * 获取服务
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public ReturnInfo getService(@RequestParam("id") UUID id) {
        DbContext context = OrmManager.instance().createDbContext(true);
        return ActionUtil.doAction(context, () -> {
            return serviceService.getService(context, id);
        });
    }

    /**
     * 删除服务
     *
     * @param bundleInfo
     * @return
     */
    @RequestMapping(value = "/service",method = RequestMethod.DELETE)
    public ReturnInfo deleteService(@RequestBody IdBundleInfo bundleInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return serviceService.deleteService(context, bundleInfo.getIds());
        });
    }

    /**
     * 查询服务
     *
     * @param queryServiceReqInfo
     * @return
     */
    @RequestMapping(value = "/service/query", method = RequestMethod.POST)
    public ReturnInfo queryService(@RequestBody QueryServiceReqInfo queryServiceReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return serviceService.queryService(context, queryServiceReqInfo);
        });
    }
}
