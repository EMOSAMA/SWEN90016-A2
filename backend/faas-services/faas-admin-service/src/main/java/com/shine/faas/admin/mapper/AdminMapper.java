package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.admin.AdminInfo;
import com.shine.faas.domain.orm.entity.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin map(AdminInfo adminInfo);
}