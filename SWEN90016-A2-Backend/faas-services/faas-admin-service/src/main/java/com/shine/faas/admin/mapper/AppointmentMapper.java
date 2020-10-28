package com.shine.faas.admin.mapper;

import com.shine.faas.admin.information.appointment.AppointmentInfo;
import com.shine.faas.domain.orm.entity.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    Appointment map(AppointmentInfo appointmentInfo);
}
