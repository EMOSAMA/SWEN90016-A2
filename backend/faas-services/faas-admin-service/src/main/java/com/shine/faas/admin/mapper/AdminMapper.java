package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.structure.department.DepartmentInfo;
import com.shine.faas.admin.information.user.admin.AdminInfo;
import com.shine.faas.domain.orm.entity.Admin;
import com.shine.faas.domain.orm.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin map(AdminInfo adminInfo);
}