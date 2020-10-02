package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.appointment.AppointmentInfo;
import com.shine.faas.domain.orm.entity.Appointment;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-30T02:19:10+1000",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_60 (Oracle Corporation)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public Appointment map(AppointmentInfo appointmentInfo) {
        if ( appointmentInfo == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setId( appointmentInfo.getId() );
        appointment.setTime( appointmentInfo.getTime() );
        appointment.setLocation( appointmentInfo.getLocation() );
        appointment.setStatus( appointmentInfo.getStatus() );
        appointment.setMessage( appointmentInfo.getMessage() );

        return appointment;
    }
}
