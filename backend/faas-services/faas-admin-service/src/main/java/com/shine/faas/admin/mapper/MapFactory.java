package com.shine.faas.admin.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapFactory {
    @Autowired
    public AdminMapper adminMapper;
    @Autowired
    public AppointmentMapper appointmentMapper;
    @Autowired
    public BillerMapper billerMapper;
    @Autowired
    public  CustomerMapper customerMapper;
    @Autowired
    public  ServiceMapper serviceMapper;
}
