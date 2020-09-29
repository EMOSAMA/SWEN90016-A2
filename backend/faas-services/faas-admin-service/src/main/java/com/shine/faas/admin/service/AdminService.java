package com.shine.faas.admin.service;

import com.shine.faas.admin.information.ModifyUserPwdInfo;
import com.shine.faas.admin.information.admin.CreateAdminReqInfo;
import com.shine.faas.admin.information.admin.QueryAdminReqInfo;
import com.shine.faas.admin.information.admin.UpdateAdminReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.admin.information.LoginReqInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface UserService {
    ReturnInfo adminLogin(DbContext context, LoginReqInfo loginReqInfo) throws Exception;

    ReturnInfo changePassword(DbContext context, ModifyUserPwdInfo modifyUserPwdInfo) throws Exception;

    ReturnInfo createAdmin(DbContext context, CreateAdminReqInfo createAdminReqInfo) throws Exception;

    ReturnInfo updateAdmin(DbContext context, UpdateAdminReqInfo updateAdminReqInfo) throws Exception;

    ReturnInfo getAdmin(DbContext context, UUID id) throws Exception;

    ReturnInfo deleteAdmin(DbContext context, List<UUID> ids) throws Exception;

    ReturnInfo queryAdmin(DbContext context, QueryAdminReqInfo queryAdminReqInfo) throws Exception;
}