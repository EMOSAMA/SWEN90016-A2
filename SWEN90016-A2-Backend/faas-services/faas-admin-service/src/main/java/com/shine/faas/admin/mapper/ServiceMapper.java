package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.service.ServiceInfo;
import com.shine.faas.domain.orm.entity.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    Service map(ServiceInfo serviceInfo);
}
