package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.customer.CustomerInfo;
import com.shine.faas.domain.orm.entity.Customer;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-29T21:00:42+1000",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer map(CustomerInfo customerInfo) {
        if ( customerInfo == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setId( customerInfo.getId() );
        customer.setName( customerInfo.getName() );
        customer.setAddress( customerInfo.getAddress() );
        customer.setPhone( customerInfo.getPhone() );
        customer.setEmail( customerInfo.getEmail() );
        customer.setPassword( customerInfo.getPassword() );
        customer.setComment( customerInfo.getComment() );

        return customer;
    }
}
