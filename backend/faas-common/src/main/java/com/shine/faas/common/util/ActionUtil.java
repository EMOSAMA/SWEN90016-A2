package com.shine.faas.common.util;

import com.shine.faas.common.DbContext;
import com.shine.faas.common.ReqCustomer;
import com.shine.faas.common.information.ReturnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

public class ActionUtil {

    private final static Logger logger = LoggerFactory.getLogger(ActionUtil.class);

    public static ReturnInfo doAction(ReqCustomer runnable) {
        ReturnInfo returnInfo = null;

        try {
            returnInfo = runnable.accept();
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
            ex.printStackTrace();
            returnInfo = new ReturnInfo(false, "服务器错误");
        } finally {

        }
        return returnInfo;
    }

    static int success_count = 0;
    static int fail_count = 0;

    public static ReturnInfo doAction(DbContext dbContext, ReqCustomer runnable) {
        if (dbContext == null) {
            logger.error("没有创建上下文对象");
            return new ReturnInfo(false, "服务器错误");
        }

        ReturnInfo returnInfo = null;
        //获取web.xml中配置的事务的管理器
//        DataSourceTransactionManager transactionManager = null;
//        TransactionStatus status = null;
        try {
            if (!dbContext.getAutoCommit()) {
//                transactionManager = (DataSourceTransactionManager)
//                        SpringUtil.getApplicationContext().getBean("transactionManager");
//                DefaultTransactionDefinition dte = new DefaultTransactionDefinition();
//                //设置隔离级别
//                dte.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//                status = SpringUtil.getTransactionManager().getTransaction(dte);

            }
            returnInfo = runnable.accept();
            if (!dbContext.getAutoCommit()) {
                if (returnInfo.isSuccess())
                    dbContext.getConnection().commit();
//                    SpringUtil.getTransactionManager().commit(status);
                else
                    dbContext.getConnection().rollback();
//                    SpringUtil.getTransactionManager().rollback(status);
                System.out.println("成功：" + ++success_count);
            }
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
            ex.printStackTrace();
            try {
                if (!dbContext.getAutoCommit())
                    dbContext.getConnection().rollback();
//                    SpringUtil.getTransactionManager().rollback(status);
            } catch (Exception ex2) {
                logger.error("事务回滚错误", ex2);
            }
            returnInfo = new ReturnInfo(false, "服务器错误");
            System.out.println("失败：" + ++fail_count);
        } finally {
            try {
                dbContext.getConnection().reset();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
//        returnInfo.setStatus(1);
        return returnInfo;
    }
}
