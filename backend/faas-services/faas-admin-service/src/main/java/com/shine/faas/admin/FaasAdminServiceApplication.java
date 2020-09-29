package com.shine.faas.admin;

import com.shine.faas.common.ApplicationStartedEvent;
import com.shine.faas.common.util.SpringUtil;
import com.shine.faas.domain.orm.OrmLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.Transactional;

//禁用JPA,引入JPA主要是为了编写sql更优化
@SpringBootApplication(exclude = JpaRepositoriesAutoConfiguration.class)
public class FaasAdminServiceApplication {

    private final static Logger logger = LoggerFactory.getLogger(FaasAdminServiceApplication.class);


    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(FaasAdminServiceApplication.class, args);


        SpringUtil.setApplicationContext(context);

        context.addApplicationListener(new ApplicationListener() {
            @Override
            @Transactional
            public void onApplicationEvent(ApplicationEvent applicationEvent) {

                if (!(applicationEvent instanceof ApplicationStartedEvent)) return;

                logger.info("启动完成...");
                SpringUtil.setTransactionManager((DataSourceTransactionManager)
                        SpringUtil.getApplicationContext().getBean("transactionManager"));
                OrmLoader.syncDatabase();

//                importService.doAction_Patrol();
//                importService.doAction_Survey();
                //importService.doAction_Task();
//                importService.doAction_Task_Patrol();
            }
        });

        context.publishEvent(new ApplicationStartedEvent("started"));

    }
}
