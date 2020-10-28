package com.shine.faas.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class SpringUtil {

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    private static ApplicationContext applicationContext;


    private static DataSourceTransactionManager transactionManager;

    public static DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static void setTransactionManager(DataSourceTransactionManager transactionManager) {
        SpringUtil.transactionManager = transactionManager;
    }
}
