package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.customer.CustomerInfo;
import com.shine.faas.domain.orm.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer map(CustomerInfo customerInfo);
}
