package com.shine.faas.admin.orm.repository;

import com.shine.faas.admin.information.user.admin.QueryAdminReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.orm.AbstractEntity;
import com.shine.faas.common.orm.CrudRepository;
import com.shine.faas.common.orm.QueryHelper;
import com.shine.faas.common.orm.query.*;
import com.shine.faas.domain.orm.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AdminRepository extends CrudRepository<Admin, UUID> {

    @Query(name = "getAuthorizeUser", value =
            "SELECT * FROM  admin WHERE username = :userName AND password = :password AND recycled = FALSE ")
    public Admin getAuthorizeUser(DbContext context, String userName, String password) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "getAuthorizeUser");
        OrmQueryInfo queryInfo = new OrmQueryInfo();
        queryInfo.getParameters().put("userName", userName);
        queryInfo.getParameters().put("password", password);
        return OrmQueryAssitant.queryOne(context, sql, queryInfo, Admin.class);
    }

    @Query(name = "queryAdmin", value =
            "SELECT * FROM  admin WHERE recycled = FALSE ")
    public PagerResultInfo<Admin> queryAdmin(DbContext context, QueryAdminReqInfo queryAdminReqInfo) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "queryAdmin");
        OrmPagerOrmQueryInfo queryInfo = new OrmPagerOrmQueryInfo();
        OrmAnd ormAnd = queryInfo.addOr().addAnd();
        if(queryAdminReqInfo.getName()!=null){
            ormAnd.addOpExpression("name", OrmOpExpression.OP_STR_CONTAINS, queryAdminReqInfo.getName());
        }
        return OrmQueryAssitant.queryPager(context, sql, queryInfo, Admin.class);
    }
}
