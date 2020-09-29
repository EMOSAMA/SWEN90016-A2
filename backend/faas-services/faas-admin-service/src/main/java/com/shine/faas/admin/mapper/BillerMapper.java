package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.biller.BillerInfo;
import com.shine.faas.domain.orm.entity.Biller;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillerMapper {
    Biller map(BillerInfo billerInfo);
}
