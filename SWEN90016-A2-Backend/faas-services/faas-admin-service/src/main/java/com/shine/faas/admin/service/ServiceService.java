package com.shine.faas.admin.service;

import com.shine.faas.admin.information.admin.CreateAdminReqInfo;
import com.shine.faas.admin.information.admin.QueryAdminReqInfo;
import com.shine.faas.admin.information.admin.UpdateAdminReqInfo;
import com.shine.faas.admin.information.service.CreateServiceReqInfo;
import com.shine.faas.admin.information.service.QueryServiceReqInfo;
import com.shine.faas.admin.information.service.UpdateServiceReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ServiceService {
    ReturnInfo createService(DbContext context, CreateServiceReqInfo createServiceReqInfo) throws Exception;

    ReturnInfo updateService(DbContext context, UpdateServiceReqInfo updateServiceReqInfo) throws Exception;

    ReturnInfo getService(DbContext context, UUID id) throws Exception;

    ReturnInfo deleteService(DbContext context, List<UUID> ids) throws Exception;

    ReturnInfo queryService(DbContext context, QueryServiceReqInfo queryServiceReqInfo) throws Exception;

    ReturnInfo queryAllService(DbContext context) throws Exception;
}
