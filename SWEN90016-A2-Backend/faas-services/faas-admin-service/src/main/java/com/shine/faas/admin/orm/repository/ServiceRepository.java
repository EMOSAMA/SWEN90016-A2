package com.shine.faas.admin.orm.repository;

import com.shine.faas.admin.information.admin.QueryAdminReqInfo;
import com.shine.faas.admin.information.service.QueryServiceReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.orm.CrudRepository;
import com.shine.faas.common.orm.QueryHelper;
import com.shine.faas.common.orm.query.*;
import com.shine.faas.domain.orm.entity.Admin;
import com.shine.faas.domain.orm.entity.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ServiceRepository extends CrudRepository<Service, UUID> {
    @Query(name = "queryService", value =
            "SELECT * FROM  service WHERE recycled = FALSE ")
    public PagerResultInfo<Service> queryService(DbContext context, QueryServiceReqInfo queryServiceReqInfo) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "queryService");
        OrmPagerOrmQueryInfo queryInfo = new OrmPagerOrmQueryInfo();
        OrmAnd ormAnd = queryInfo.addOr().addAnd();
        if(queryServiceReqInfo.getServiceName()!=null){
            ormAnd.addOpExpression("service_name", OrmOpExpression.OP_STR_CONTAINS, queryServiceReqInfo.getServiceName());
        }

        queryInfo.setPagerInfo(queryServiceReqInfo);
        queryInfo.getOrderByList().add(new OrderBy("create_time", false));
        return OrmQueryAssitant.queryPager(context, sql, queryInfo, Service.class);
    }

    @Query(name = "queryAllService", value =
            "SELECT * FROM  service WHERE recycled = FALSE ")
    public List<Service> queryAllService(DbContext context) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "queryAllService");
        OrmPagerOrmQueryInfo queryInfo = new OrmPagerOrmQueryInfo();
        OrmAnd ormAnd = queryInfo.addOr().addAnd();
        return OrmQueryAssitant.queryList(context, sql, queryInfo, Service.class);
    }
}