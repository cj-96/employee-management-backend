package org.example.service;

import java.util.Map;

import org.example.dto.Employee;

import java.util.List;

public interface EmployeeService {
    Employee persist(Employee employee);

    List<Employee> retrieveAll();

    Employee retrieveById(Long id);

    Employee update(Long id, Employee employeeDetails);

    void delete(Long id);

    Integer retrieveCount();

    Integer retrieveCountOnLeave();

    Integer retrieveCountAtWork();

    Integer retrieveCountPending();

    Map<String,Integer> getEmployeeCountPerDepartment();
}
