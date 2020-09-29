package com.shine.faas.admin.orm.repository;

import com.shine.faas.admin.information.admin.QueryAdminReqInfo;
import com.shine.faas.admin.information.customer.QueryCustomerReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.orm.CrudRepository;
import com.shine.faas.common.orm.QueryHelper;
import com.shine.faas.common.orm.query.*;
import com.shine.faas.domain.orm.entity.Admin;
import com.shine.faas.domain.orm.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerRepository extends CrudRepository<Customer, UUID> {

    @Query(name = "getAuthorizeUser", value =
            "SELECT * FROM  customer WHERE username = :userName AND password = :password AND recycled = FALSE ")
    public Customer getAuthorizeUser(DbContext context, String userName, String password) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "getAuthorizeUser");
        OrmQueryInfo queryInfo = new OrmQueryInfo();
        queryInfo.getParameters().put("userName", userName);
        queryInfo.getParameters().put("password", password);
        return OrmQueryAssitant.queryOne(context, sql, queryInfo, Admin.class);
    }

    @Query(name = "queryCustomer", value =
            "SELECT * FROM  customer WHERE recycled = FALSE ")
    public PagerResultInfo<Customer> queryCustomer(DbContext context, QueryCustomerReqInfo queryCustomerReqInfo) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "queryCustomer");
        OrmPagerOrmQueryInfo queryInfo = new OrmPagerOrmQueryInfo();
        OrmAnd ormAnd = queryInfo.addOr().addAnd();
        if(queryCustomerReqInfo.getName()!=null){
            ormAnd.addOpExpression("name", OrmOpExpression.OP_STR_CONTAINS, queryCustomerReqInfo.getName());
        }
        return OrmQueryAssitant.queryPager(context, sql, queryInfo, Customer.class);
    }
}