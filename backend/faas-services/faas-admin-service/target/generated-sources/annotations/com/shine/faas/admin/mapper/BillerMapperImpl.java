package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.biller.BillerInfo;
import com.shine.faas.domain.orm.entity.Biller;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-30T02:19:10+1000",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_60 (Oracle Corporation)"
)
@Component
public class BillerMapperImpl implements BillerMapper {

    @Override
    public Biller map(BillerInfo billerInfo) {
        if ( billerInfo == null ) {
            return null;
        }

        Biller biller = new Biller();

        biller.setId( billerInfo.getId() );
        biller.setName( billerInfo.getName() );
        biller.setEmail( billerInfo.getEmail() );

        return biller;
    }
}
