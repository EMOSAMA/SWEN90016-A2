package com.shine.faas.admin.service.impl;

import com.shine.faas.admin.information.biller.CreateBillerReqInfo;
import com.shine.faas.admin.information.biller.QueryBillerReqInfo;
import com.shine.faas.admin.information.biller.UpdateBillerReqInfo;
import com.shine.faas.admin.information.service.CreateServiceReqInfo;
import com.shine.faas.admin.information.service.QueryServiceReqInfo;
import com.shine.faas.admin.information.service.UpdateServiceReqInfo;
import com.shine.faas.admin.mapper.MapFactory;
import com.shine.faas.admin.orm.RepositoryFactory;
import com.shine.faas.admin.service.BillerService;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.common.orm.query.PagerResultInfo;
import com.shine.faas.domain.orm.entity.Biller;
import com.shine.faas.domain.orm.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class BillerServiceImpl implements BillerService {
    @Autowired
    private RepositoryFactory repositoryFactory;

    @Autowired
    private MapFactory mapFactory;

    @Override
    public ReturnInfo createBiller(DbContext context, CreateBillerReqInfo createBillerReqInfo) throws Exception{
        Biller biller =this.mapFactory.billerMapper.map(createBillerReqInfo);
        biller.setId(UUID.randomUUID());
        biller.setCreateTime(new Date());
        this.repositoryFactory.billerRepository.insert(context,biller);

        return (new ReturnInfo(biller.getId()));
    }
    @Override
    public ReturnInfo updateBiller(DbContext context, UpdateBillerReqInfo updateBillerReqInfo) throws Exception{
        Biller oldBiller=this.repositoryFactory.billerRepository.findOne(context, updateBillerReqInfo.getId());
        if(oldBiller==null){
            return (new ReturnInfo(false,"Biller not exist"));
        }
        Biller biller=this.mapFactory.billerMapper.map(updateBillerReqInfo);
        biller.setCreateTime(oldBiller.getCreateTime());
        biller.setUpdateTime(new Date());

        this.repositoryFactory.billerRepository.update(context,biller);
        return new ReturnInfo(true,"Update successful");
    }
    @Override
    public ReturnInfo getBiller(DbContext context, UUID id) throws Exception{
        Biller biller = repositoryFactory.billerRepository.findOne(context,id);
        return new ReturnInfo(biller);
    }
    @Override
    public ReturnInfo deleteBiller(DbContext context, List<UUID> ids) throws Exception{
        List<Biller> billers= this.repositoryFactory.billerRepository.findAll(context,ids);
        billers.forEach(biller -> {
            biller.setRecycled(true);
            biller.setUpdateTime(new Date());
        });
        this.repositoryFactory.billerRepository.update(context,billers);
        return new ReturnInfo(true,"Delete Successful");
    }
    @Override
    public ReturnInfo queryBiller(DbContext context, QueryBillerReqInfo queryBillerReqInfo) throws Exception{
        PagerResultInfo<Biller> resultInfo =this.repositoryFactory.billerRepository.queryBiller(context,queryBillerReqInfo);
        return new ReturnInfo(resultInfo);
    }
}
