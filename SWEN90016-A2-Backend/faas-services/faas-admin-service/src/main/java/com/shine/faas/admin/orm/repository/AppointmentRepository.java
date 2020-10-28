package com.shine.faas.admin.orm.repository;

import com.shine.faas.admin.information.appointment.QueryAppointmentReqInfo;
import com.shine.faas.admin.information.appointment.QueryAppointmentResInfo;
import com.shine.faas.admin.information.biller.QueryBillerReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.orm.CrudRepository;
import com.shine.faas.common.orm.QueryHelper;
import com.shine.faas.common.orm.query.*;
import com.shine.faas.domain.orm.entity.Admin;
import com.shine.faas.domain.orm.entity.Appointment;
import com.shine.faas.domain.orm.entity.Biller;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppointmentRepository extends CrudRepository<Appointment, UUID> {
    @Query(name = "queryAppointment", value =
            "SELECT a.*,c.name,s.service_name FROM  appointment a LEFT JOIN customer c ON a.customer_id = c.id LEFT JOIN service s ON a.service_id = s.id WHERE a.recycled = FALSE ")
    public PagerResultInfo<QueryAppointmentResInfo> queryAppointment(DbContext context, QueryAppointmentReqInfo queryAppointmentReqInfo) throws Exception {
        String sql = QueryHelper.getQuery(this.getClass(), "queryAppointment");
        OrmPagerOrmQueryInfo queryInfo = new OrmPagerOrmQueryInfo();
        OrmAnd ormAnd = queryInfo.addOr().addAnd();
        if(queryAppointmentReqInfo.getServiceId()!=null){
            ormAnd.addOpExpression("service_id", OrmOpExpression.OP_EQ, queryAppointmentReqInfo.getServiceId());
        }
        if(queryAppointmentReqInfo.getCustomerId()!=null){
            ormAnd.addOpExpression("customer_id", OrmOpExpression.OP_EQ, queryAppointmentReqInfo.getCustomerId());
        }
        if(queryAppointmentReqInfo.getStatus()!=null){
            ormAnd.addOpExpression("status", OrmOpExpression.OP_EQ, queryAppointmentReqInfo.getStatus());
        }
        if(queryAppointmentReqInfo.getCustomerName()!=null){
            ormAnd.addOpExpression("name", OrmOpExpression.OP_STR_CONTAINS, queryAppointmentReqInfo.getCustomerName());
        }
        if(queryAppointmentReqInfo.getDate()!=null){
            ormAnd.addOpExpression("date", OrmOpExpression.OP_EQ, queryAppointmentReqInfo.getDate());
        }
        if(queryAppointmentReqInfo.getTime()!=null){
            ormAnd.addOpExpression("time", OrmOpExpression.OP_EQ, queryAppointmentReqInfo.getTime());
        }

        queryInfo.setPagerInfo(queryAppointmentReqInfo);
        queryInfo.getOrderByList().add(new OrderBy("date", false));
        queryInfo.getOrderByList().add(new OrderBy("time", false));
        return OrmQueryAssitant.queryPager(context, sql, queryInfo, QueryAppointmentResInfo.class);
    }
}
