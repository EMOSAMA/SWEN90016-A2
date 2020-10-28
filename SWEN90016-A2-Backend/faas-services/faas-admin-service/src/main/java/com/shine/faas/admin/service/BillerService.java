package com.shine.faas.admin.service;

import com.shine.faas.admin.information.biller.CreateBillerReqInfo;
import com.shine.faas.admin.information.biller.QueryBillerReqInfo;
import com.shine.faas.admin.information.biller.UpdateBillerReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface BillerService {
    ReturnInfo createBiller(DbContext context, CreateBillerReqInfo createBillerReqInfo) throws Exception;

    ReturnInfo updateBiller(DbContext context, UpdateBillerReqInfo updateBillerReqInfo) throws Exception;

    ReturnInfo getBiller(DbContext context, UUID id) throws Exception;

    ReturnInfo deleteBiller(DbContext context, List<UUID> ids) throws Exception;

    ReturnInfo queryBiller(DbContext context, QueryBillerReqInfo queryBillerReqInfo) throws Exception;
}
