package com.shine.faas.admin.service.impl;

import com.shine.faas.admin.information.appointment.CreateAppointmentReqInfo;
import com.shine.faas.admin.information.appointment.QueryAppointmentReqInfo;
import com.shine.faas.admin.information.appointment.QueryAppointmentResInfo;
import com.shine.faas.admin.information.appointment.UpdateAppointmentReqInfo;
import com.shine.faas.admin.information.service.UpdateServiceReqInfo;
import com.shine.faas.admin.mapper.MapFactory;
import com.shine.faas.admin.orm.RepositoryFactory;
import com.shine.faas.admin.service.AppointmentService;
import com.shine.faas.common.DbContext;
import com.shine.faas.common.information.ReturnInfo;
import com.shine.faas.common.orm.query.PagerResultInfo;
import com.shine.faas.domain.orm.entity.Appointment;
import com.shine.faas.domain.orm.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private RepositoryFactory repositoryFactory;

    @Autowired
    private MapFactory mapFactory;

    @Override
    public ReturnInfo createAppointment(DbContext context, CreateAppointmentReqInfo createAppointmentReqInfo) throws Exception{
        Appointment appointment=this.mapFactory.appointmentMapper.map(createAppointmentReqInfo);
        appointment.setId(UUID.randomUUID());
        appointment.setCreateTime(new Date());
        this.repositoryFactory.appointmentRepository.insert(context,appointment);

        return (new ReturnInfo(appointment.getId()));
    }
    @Override
    public ReturnInfo updateAppointment(DbContext context, UpdateAppointmentReqInfo updateAppointmentReqInfo) throws Exception{
        Appointment oldAppointment=this.repositoryFactory.appointmentRepository.findOne(context, updateAppointmentReqInfo.getId());
        if(oldAppointment==null){
            return (new ReturnInfo(false,"Appointment not exist"));
        }
        Appointment appointment=this.mapFactory.appointmentMapper.map(updateAppointmentReqInfo);
        appointment.setCreateTime(oldAppointment.getCreateTime());
        appointment.setUpdateTime(new Date());

        this.repositoryFactory.appointmentRepository.update(context,appointment);
        return new ReturnInfo(true,"Update successful");
    }

    @Override
    public ReturnInfo getAppointment(DbContext context, UUID id) throws Exception{
        Appointment appointment = repositoryFactory.appointmentRepository.findOne(context,id);
        return new ReturnInfo(appointment);
    }

    @Override
    public ReturnInfo deleteAppointment(DbContext context, List<UUID> ids) throws Exception{
        List<Appointment> appointments= this.repositoryFactory.appointmentRepository.findAll(context,ids);
        appointments.forEach(appointment -> {
            appointment.setRecycled(true);
            appointment.setUpdateTime(new Date());
        });
        this.repositoryFactory.appointmentRepository.update(context,appointments);
        return new ReturnInfo(true,"Delete Successful");
    }

    @Override
    public ReturnInfo queryAppointment(DbContext context, QueryAppointmentReqInfo queryAppointmentReqInfo) throws Exception{
        PagerResultInfo<QueryAppointmentResInfo> resultInfo =this.repositoryFactory.appointmentRepository.queryAppointment(context,queryAppointmentReqInfo);
        return new ReturnInfo(resultInfo);
    }
}
