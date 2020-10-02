package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.admin.AdminInfo;
import com.shine.faas.domain.orm.entity.Admin;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-30T02:19:09+1000",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_60 (Oracle Corporation)"
)
@Component
public class AdminMapperImpl implements AdminMapper {

    @Override
    public Admin map(AdminInfo adminInfo) {
        if ( adminInfo == null ) {
            return null;
        }

        Admin admin = new Admin();

        admin.setId( adminInfo.getId() );
        admin.setPassword( adminInfo.getPassword() );
        admin.setName( adminInfo.getName() );
        admin.setEmail( adminInfo.getEmail() );

        return admin;
    }
}
