package com.shine.faas.admin.orm;

import com.shine.faas.admin.orm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {
    @Autowired
    public AdminRepository adminRepository;

    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public DepartmentRepository departmentRepository;

    @Autowired
    public MajorRepository majorRepository;

    @Autowired
    public TeacherRepository teacherRepository;

    @Autowired
    public UnitRepository unitRepository;

    @Autowired
    public SemesterRepository semesterRepository;

    @Autowired
    public ClassesRepository classesRepository;

    @Autowired
    public AllocateUnitsRepository allocateUnitsRepository;

    @Autowired
    public GradesRepository gradesRepository;

    @Autowired
    public ManageUnitsRepository manageUnitsRepository;

    @Autowired
    public ApplyProjectRepository applyProjectRepository;

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    public RentBookRepository rentBookRepository;
}
