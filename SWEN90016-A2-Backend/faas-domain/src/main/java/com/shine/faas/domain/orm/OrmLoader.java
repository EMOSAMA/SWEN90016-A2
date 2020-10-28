package com.shine.faas.domain.orm;

import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.common.orm.BaseEntity;
import com.shine.faas.common.orm.OrmManager;
import com.shine.faas.common.util.ActionUtil;
import com.shine.faas.domain.orm.entity.*;

import java.util.ArrayList;
import java.util.List;

public class OrmLoader {

    public static void syncDatabase() {
        List<BaseEntity> entities = new ArrayList<>();
        entities.add(new Admin());
        entities.add(new Biller());
        entities.add(new Service());
        entities.add(new Customer());
        entities.add(new Appointment());
        DbContext dbContext1 = OrmManager.instance().createDbContext(false);
        ActionUtil.doAction(dbContext1, () -> {
            OrmManager.instance().init(dbContext1, entities);
            return new ReturnInfo();
        });
    }
}
