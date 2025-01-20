package org.example.service;

import org.example.dto.Department;

import java.util.List;

public interface DepartmentService {
    Department persist(Department department);

    List<Department> retrieveAll();

    Department retrieveById(Long id);

    Department update(Long id, Department departmentDetails);

    void delete(Long id);
}
