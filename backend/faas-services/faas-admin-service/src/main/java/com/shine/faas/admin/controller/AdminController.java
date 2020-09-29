package com.shine.faas.admin.controller;

import com.shine.faas.admin.information.ModifyPwdInfo;
import com.shine.faas.admin.information.admin.CreateAdminReqInfo;
import com.shine.faas.admin.information.admin.QueryAdminReqInfo;
import com.shine.faas.admin.information.admin.UpdateAdminReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.common.orm.OrmManager;
import com.shine.faas.common.util.ActionUtil;
import com.shine.faas.admin.information.LoginReqInfo;
import com.shine.faas.admin.service.AdminService;
import com.shine.faas.information.IdBundleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 使用 @RestController 定义一个控制器
 */
@RestController()
@RequestMapping(value = "")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录后台
     *
     * @param loginReqInfo
     * @return
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public ReturnInfo adminLogin(@RequestBody LoginReqInfo loginReqInfo) {

        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return adminService.adminLogin(context, loginReqInfo);
        });
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "/admin/password", method = RequestMethod.PUT)
    public ReturnInfo adminChangePassword(@RequestBody ModifyPwdInfo modifyUserPwdInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return adminService.changePassword(context, modifyUserPwdInfo);
        });
    }

    //管理员账户相关接口
    /**
     * 创建管理员
     *
     * @param createAdminReqInfo
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ReturnInfo createAdmin(@RequestBody CreateAdminReqInfo createAdminReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return adminService.createAdmin(context, createAdminReqInfo);
        });
    }

    /**
     * 更新管理员
     *
     * @param updateAdminReqInfo
     * @return
     */
    @RequestMapping(value = "/admin",method = RequestMethod.PUT)
    public ReturnInfo updateAdmin(@RequestBody UpdateAdminReqInfo updateAdminReqInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return adminService.updateAdmin(context,updateAdminReqInfo);
        });
    }

    /**
     * 获取管理员
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ReturnInfo getAdmin(@RequestParam("id") UUID id) {
        DbContext context = OrmManager.instance().createDbContext(true);
        return ActionUtil.doAction(context, () -> {
            return adminService.getAdmin(context, id);
        });
    }

    /**
     * 删除管理员
     *
     * @param bundleInfo
     * @return
     */
    @RequestMapping(value = "/admin",method = RequestMethod.DELETE)
    public ReturnInfo deleteAdmin(@RequestBody IdBundleInfo bundleInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return adminService.deleteAdmin(context, bundleInfo.getIds());
        });
    }

    /**
     * 查询管理员
     *
     * @param queryAdminReqInfo
     * @return
     */
    @RequestMapping(value = "/admin/query", method = RequestMethod.POST)
    public ReturnInfo queryAdmin(@RequestBody QueryAdminReqInfo queryAdminReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return adminService.queryAdmin(context, queryAdminReqInfo);
        });
    }
}