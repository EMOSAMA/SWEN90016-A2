package com.shine.faas.admin.service.impl;


import com.shine.faas.admin.information.*;
import com.shine.faas.admin.information.admin.CreateAdminReqInfo;
import com.shine.faas.admin.information.admin.QueryAdminReqInfo;
import com.shine.faas.admin.information.admin.UpdateAdminReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.admin.mapper.MapFactory;
import com.shine.faas.admin.orm.RepositoryFactory;
import com.shine.faas.admin.service.UserService;
import com.shine.faas.common.orm.query.PagerResultInfo;
import com.shine.faas.domain.orm.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private RepositoryFactory repositoryFactory;

    @Autowired
    private MapFactory mapFactory;

    @Override
    public ReturnInfo adminLogin(DbContext context, LoginReqInfo loginReqInfo) throws Exception {

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
    public ReturnInfo changePassword(DbContext context, ModifyUserPwdInfo modifyUserPwdInfo) throws Exception {

        if (modifyUserPwdInfo.getUserId() == null)
            return new ReturnInfo(false, "必须指定用户");

        if (modifyUserPwdInfo.getOldPassword() == null)
            return new ReturnInfo(false, "必须传入旧密码");

        if (modifyUserPwdInfo.getPassword() == null)
            return new ReturnInfo(false, "密码必须非空");

        if (modifyUserPwdInfo.getPassword().length() < 6)
            return new ReturnInfo(false, "密码长度不能小于6位");

        if (modifyUserPwdInfo.getPassword().length() > 12)
            return new ReturnInfo(false, "密码长度不能大于12位");

        Admin user = this.repositoryFactory.adminRepository.findOne(context, modifyUserPwdInfo.getUserId());
        if (user == null || user.getRecycled())
            return new ReturnInfo(false, "用户不存在");


        if (!user.getPassword().equals(modifyUserPwdInfo.getOldPassword()))
            return new ReturnInfo(false, "输入的旧密码错误");

        user.setPassword(modifyUserPwdInfo.getPassword());
        user.setUpdateTime(new Date());
        this.repositoryFactory.adminRepository.update(context, user);

        return new ReturnInfo();
    }

    //管理员相关服务重载
    @Override
    public ReturnInfo createAdmin(DbContext context, CreateAdminReqInfo createAdminReqInfo) throws Exception{
        Admin admin=this.mapFactory.adminMapper.map(createAdminReqInfo);
        admin.setId(UUID.randomUUID());
        admin.setCreateTime(new Date());
        this.repositoryFactory.adminRepository.insert(context,admin);

        return (new ReturnInfo(admin.getId()));
    }

    @Override
    public ReturnInfo updateAdmin(DbContext context, UpdateAdminReqInfo updateAdminReqInfo) throws Exception{
        Admin oldAdmin=this.repositoryFactory.adminRepository.findOne(context, updateAdminReqInfo.getId());
        if(oldAdmin==null){
            return (new ReturnInfo(false,"该管理员不存在"));
        }
        Admin admin=this.mapFactory.adminMapper.map(updateAdminReqInfo);
        admin.setCreateTime(oldAdmin.getCreateTime());
        admin.setUpdateTime(new Date());

        this.repositoryFactory.adminRepository.update(context,admin);
        return new ReturnInfo(true,"更新成功");

    }

    @Override
    public ReturnInfo getAdmin(DbContext context, UUID id) throws Exception{
        Admin admin=this.repositoryFactory.adminRepository.findOne(context,id);
        return new ReturnInfo(admin);
    }

    @Override
    public ReturnInfo deleteAdmin(DbContext context, List<UUID> ids) throws Exception{
        List<Admin> admins= this.repositoryFactory.adminRepository.findAll(context,ids);
        admins.forEach(admin -> {
            admin.setRecycled(true);
            admin.setUpdateTime(new Date());
        });
        this.repositoryFactory.adminRepository.update(context,admins);
        return new ReturnInfo(true,"删除成功");
    }

    @Override
    public ReturnInfo queryAdmin(DbContext context, QueryAdminReqInfo queryAdminReqInfo) throws Exception{
        PagerResultInfo<Admin> resultInfo =this.repositoryFactory.adminRepository.queryAdmin(context,queryAdminReqInfo);
        return new ReturnInfo(resultInfo);
    }
}
