package com.shine.faas.admin.service;

import com.shine.faas.admin.information.appointment.CreateAppointmentReqInfo;
import com.shine.faas.admin.information.appointment.QueryAppointmentReqInfo;
import com.shine.faas.admin.information.appointment.QueryAppointmentResInfo;
import com.shine.faas.admin.information.appointment.UpdateAppointmentReqInfo;
import com.shine.faas.admin.information.service.CreateServiceReqInfo;
import com.shine.faas.admin.information.service.QueryServiceReqInfo;
import com.shine.faas.admin.information.service.UpdateServiceReqInfo;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface AppointmentService {
    ReturnInfo createAppointment(DbContext context, CreateAppointmentReqInfo createAppointmentReqInfo) throws Exception;

    ReturnInfo updateAppointment(DbContext context, UpdateAppointmentReqInfo updateAppointmentReqInfo) throws Exception;

    ReturnInfo getAppointment(DbContext context, UUID id) throws Exception;

    ReturnInfo deleteAppointment(DbContext context, List<UUID> ids) throws Exception;

    ReturnInfo queryAppointment(DbContext context, QueryAppointmentReqInfo queryAppointmentReqInfo) throws Exception;
}
