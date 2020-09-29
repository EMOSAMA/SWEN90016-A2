package com.shine.faas.common.orm;

import com.shine.faas.common.SQLConnection;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.util.SpringUtil;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

//@Component
public class OrmManager {

//    private JDBCClient client;

//    @Autowired
//    private DatabaseSync databaseSync;

//    private JsonObject config;

//    private Connection getConnection() {
//        String driver = config.get("driver_class").toString();
//        String url = config.get("url").toString();
//        String username = config.get("user").toString();
//        String password = config.get("password").toString();
//        Connection conn = null;
//        try {
////            Class.forName(driver); //classLoader,加载对应驱动
//            conn = DriverManager.getConnection(url, username, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }

    public void init(DbContext dbContext, List<BaseEntity> entities) {

//        this.config = dbConfig;

//        client = JDBCClient.createShared(vertx, dbConfig);

//        SQLConnection connection = awaitResult(client::getConnection);
//
        DatabaseSync databaseSync = new DatabaseSync();
        databaseSync.sync(dbContext, entities);

//        Void vod = awaitResult(h -> connection.setAutoCommit(false, h));
//        try {
//            databaseSync.sync(connection, entities);
//            vod = awaitResult(h -> connection.commit(h));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            vod = awaitResult(h -> connection.rollback(h));
//        }

    }

//    public JDBCClient getClient() {
//        return this.client;
//    }


    public DbContext createDbContext() {
        return createDbContext(true);
    }


    public DbContext createDbContext(Boolean autoCommit) {
        try {
            DbContext dbContext = new DbContext();
            SQLConnection sqlConnection = new SQLConnection(SpringUtil.getApplicationContext().getBean(NamedParameterJdbcTemplate.class));// awaitResult(client::getConnection);
            sqlConnection.setAutoCommit(autoCommit);
            dbContext.setAutoCommit(autoCommit);
            dbContext.setConnection(sqlConnection);
            return dbContext;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private OrmManager() {

    }

    private static OrmManager _instance = new OrmManager();

    public static OrmManager instance() {
        return _instance;
    }


}
