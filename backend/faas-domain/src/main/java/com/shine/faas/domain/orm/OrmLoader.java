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
        entities.add(new Student());
        entities.add(new Teacher());
        entities.add(new Admin());
        entities.add(new Department());
        entities.add(new Major());
        entities.add(new Unit());
        entities.add(new AllocateUnits());
        entities.add(new Classes());
        entities.add(new Grades());
        entities.add(new Semester());
        entities.add(new ManageUnits());
        entities.add(new ApplyProject());
        entities.add(new Book());
        entities.add(new RentBook());
        DbContext dbContext1 = OrmManager.instance().createDbContext(false);
        ActionUtil.doAction(dbContext1, () -> {
            OrmManager.instance().init(dbContext1, entities);
            return new ReturnInfo();
        });
    }
}
