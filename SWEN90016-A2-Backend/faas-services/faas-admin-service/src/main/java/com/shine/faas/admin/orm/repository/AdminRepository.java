package com.shine.faas.admin.orm.repository;

import com.shine.faas.admin.information.admin.QueryAdminReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.orm.CrudRepository;
import com.shine.faas.common.orm.QueryHelper;
import com.shine.faas.common.orm.query.*;
import com.shine.faas.domain.orm.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AdminRepository extends CrudRepository<Admin, UUID> {

    @Query(name = "getAuthorizeUser", value =
            "SELECT * FROM  admin WHERE email = :email AND password = :password AND recycled = FALSE ")
    public Admin getAuthorizeUser(DbContext context, String userName, String password) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "getAuthorizeUser");
        OrmQueryInfo queryInfo = new OrmQueryInfo();
        queryInfo.getParameters().put("email", userName);
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

        queryInfo.setPagerInfo(queryAdminReqInfo);
        queryInfo.getOrderByList().add(new OrderBy("create_time", false));
        return OrmQueryAssitant.queryPager(context, sql, queryInfo, Admin.class);
    }

    @Query(name = "queryAllAdmin", value =
            "SELECT * FROM  admin WHERE recycled = FALSE ")
    public List<Admin> queryAllAdmin(DbContext context) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "queryAllAdmin");
        OrmPagerOrmQueryInfo queryInfo = new OrmPagerOrmQueryInfo();
        OrmAnd ormAnd = queryInfo.addOr().addAnd();
        return OrmQueryAssitant.queryList(context, sql, queryInfo, Admin.class);
    }
}
