package com.shine.faas.admin.orm;

import com.shine.faas.admin.orm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {
    @Autowired
    public AdminRepository adminRepository;
    @Autowired
    public AppointmentRepository appointmentRepository;
    @Autowired
    public BillerRepository billerRepository;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public ServiceRepository serviceRepository;
}
