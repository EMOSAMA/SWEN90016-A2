package com.shine.faas.admin.orm.repository;

import com.shine.faas.admin.information.biller.QueryBillerReqInfo;
import com.shine.faas.admin.information.service.QueryServiceReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.orm.CrudRepository;
import com.shine.faas.common.orm.QueryHelper;
import com.shine.faas.common.orm.query.*;
import com.shine.faas.domain.orm.entity.Appointment;
import com.shine.faas.domain.orm.entity.Biller;
import com.shine.faas.domain.orm.entity.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BillerRepository extends CrudRepository<Biller, UUID> {
    @Query(name = "queryBiller", value =
            "SELECT * FROM  biller WHERE recycled = FALSE ")
    public PagerResultInfo<Biller> queryBiller(DbContext context, QueryBillerReqInfo queryBillerReqInfo) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "queryService");
        OrmPagerOrmQueryInfo queryInfo = new OrmPagerOrmQueryInfo();
        OrmAnd ormAnd = queryInfo.addOr().addAnd();
        if(queryBillerReqInfo.getCustomerId()!=null){
            ormAnd.addOpExpression("customer_id", OrmOpExpression.OP_EQ, queryBillerReqInfo.getCustomerId());
        }
        return OrmQueryAssitant.queryPager(context, sql, queryInfo, Biller.class);
    }
}