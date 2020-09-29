package com.shine.faas.admin.service.impl;

import com.shine.faas.admin.information.service.CreateServiceReqInfo;
import com.shine.faas.admin.information.service.QueryServiceReqInfo;
import com.shine.faas.admin.information.service.UpdateServiceReqInfo;
import com.shine.faas.admin.mapper.MapFactory;
import com.shine.faas.admin.orm.RepositoryFactory;
import com.shine.faas.admin.service.ServiceService;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.common.orm.query.PagerResultInfo;
import com.shine.faas.domain.orm.entity.Admin;
import com.shine.faas.domain.orm.entity.Service;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    private RepositoryFactory repositoryFactory;

    @Autowired
    private MapFactory mapFactory;

    @Override
    public ReturnInfo createService(DbContext context, CreateServiceReqInfo createServiceReqInfo) throws Exception{
        Service service=this.mapFactory.serviceMapper.map(createServiceReqInfo);
        service.setId(UUID.randomUUID());
        service.setCreateTime(new Date());
        this.repositoryFactory.serviceRepository.insert(context,service);

        return (new ReturnInfo(service.getId()));
    }
    @Override
    public ReturnInfo updateService(DbContext context, UpdateServiceReqInfo updateServiceReqInfo) throws Exception{
        Service oldService=this.repositoryFactory.serviceRepository.findOne(context, updateServiceReqInfo.getId());
        if(oldService==null){
            return (new ReturnInfo(false,"Service not exist"));
        }
        Service service=this.mapFactory.serviceMapper.map(updateServiceReqInfo);
        service.setCreateTime(oldService.getCreateTime());
        service.setUpdateTime(new Date());

        this.repositoryFactory.serviceRepository.update(context,service);
        return new ReturnInfo(true,"Update successful");
    }
    @Override
    public ReturnInfo getService(DbContext context, UUID id) throws Exception{
        Service service = repositoryFactory.serviceRepository.findOne(context,id);
        return new ReturnInfo(service);
    }
    @Override
    public ReturnInfo deleteService(DbContext context, List<UUID> ids) throws Exception{
        List<Service> services= this.repositoryFactory.serviceRepository.findAll(context,ids);
        services.forEach(service -> {
            service.setRecycled(true);
            service.setUpdateTime(new Date());
        });
        this.repositoryFactory.serviceRepository.update(context,services);
        return new ReturnInfo(true,"Delete Successful");
    }
    @Override
    public ReturnInfo queryService(DbContext context, QueryServiceReqInfo queryServiceReqInfo) throws Exception{
        PagerResultInfo<Service> resultInfo =this.repositoryFactory.serviceRepository.queryService(context,queryServiceReqInfo);
        return new ReturnInfo(resultInfo);
    }
}
