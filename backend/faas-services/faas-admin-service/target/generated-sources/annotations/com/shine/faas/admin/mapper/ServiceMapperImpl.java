package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.service.ServiceInfo;
import com.shine.faas.domain.orm.entity.Service;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-29T21:00:42+1000",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public Service map(ServiceInfo serviceInfo) {
        if ( serviceInfo == null ) {
            return null;
        }

        Service service = new Service();

        service.setId( serviceInfo.getId() );
        service.setServiceNanme( serviceInfo.getServiceNanme() );
        service.setCost( serviceInfo.getCost() );
        service.setDuration( serviceInfo.getDuration() );

        return service;
    }
}
