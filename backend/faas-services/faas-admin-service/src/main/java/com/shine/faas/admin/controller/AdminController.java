package com.shine.faas.admin.controller;

import com.shine.faas.admin.information.ModifyUserPwdInfo;
import com.shine.faas.admin.information.admin.CreateAdminReqInfo;
import com.shine.faas.admin.information.admin.QueryAdminReqInfo;
import com.shine.faas.admin.information.admin.UpdateAdminReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.common.orm.OrmManager;
import com.shine.faas.common.util.ActionUtil;
import com.shine.faas.admin.information.LoginReqInfo;
import com.shine.faas.admin.service.UserService;
import com.shine.faas.information.IdBundleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 使用 @RestController 定义一个控制器
 */
@RestController()
@RequestMapping(value = "")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录后台
     *
     * @param loginReqInfo
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnInfo login(@RequestBody LoginReqInfo loginReqInfo) {

        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return userService.adminLogin(context, loginReqInfo);
        });
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "/admin-user/password", method = RequestMethod.PUT)
    public ReturnInfo changePassword(@RequestBody ModifyUserPwdInfo modifyUserPwdInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return userService.changePassword(context, modifyUserPwdInfo);
        });
    }

    //管理员账户相关接口
    /**
     * 创建管理员
     *
     * @param createAdminReqInfo
     * @return
     */
    @RequestMapping(value = "/user/admin", method = RequestMethod.POST)
    public ReturnInfo createAdmin(@RequestBody CreateAdminReqInfo createAdminReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return userService.createAdmin(context, createAdminReqInfo);
        });
    }

    /**
     * 更新管理员
     *
     * @param updateAdminReqInfo
     * @return
     */
    @RequestMapping(value = "/user/admin",method = RequestMethod.PUT)
    public ReturnInfo updateAdmin(@RequestBody UpdateAdminReqInfo updateAdminReqInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return userService.updateAdmin(context,updateAdminReqInfo);
        });
    }

    /**
     * 获取管理员
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/admin", method = RequestMethod.GET)
    public ReturnInfo getAdmin(@RequestParam("id") UUID id) {
        DbContext context = OrmManager.instance().createDbContext(true);
        return ActionUtil.doAction(context, () -> {
            return userService.getAdmin(context, id);
        });
    }

    /**
     * 删除管理员
     *
     * @param bundleInfo
     * @return
     */
    @RequestMapping(value = "/user/admin",method = RequestMethod.DELETE)
    public ReturnInfo deleteAdmin(@RequestBody IdBundleInfo bundleInfo){
        DbContext context =OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context,()->{
            return userService.deleteAdmin(context, bundleInfo.getIds());
        });
    }

    /**
     * 查询管理员
     *
     * @param queryAdminReqInfo
     * @return
     */
    @RequestMapping(value = "/user/admin/query", method = RequestMethod.POST)
    public ReturnInfo queryAdmin(@RequestBody QueryAdminReqInfo queryAdminReqInfo) {
        DbContext context = OrmManager.instance().createDbContext(false);
        return ActionUtil.doAction(context, () -> {
            return userService.queryAdmin(context, queryAdminReqInfo);
        });
    }
}