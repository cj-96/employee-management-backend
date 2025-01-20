package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;
import org.example.exception.EmployeeException;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final ModelMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "Employee not found with ID: ";

    @Override
    public Employee persist(Employee employee) {
        try {
            EmployeeEntity save = repository.save(mapper.map(employee, EmployeeEntity.class));
            return mapper.map(save, Employee.class);
        } catch (DataAccessException e) {
            log.error("Failed to persist employee: {}", employee, e);
            throw new EmployeeException("Failed to save employee to database");
        } catch (MappingException e) {
            log.error("Error mapping employee object: {}", employee, e);
            throw new EmployeeException("Failed to convert employee data");
        }
    }

    @Override
    public List<Employee> retrieveAll() {
        try {
            List<Employee> list = new ArrayList<>();
            List<EmployeeEntity> all = repository.findAll();

            if (all.isEmpty()) {
                throw new EmployeeException("Employee Not Found");
            }
            all.forEach(item -> list.add(mapper.map(item, Employee.class)));

            return list;
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving employee records", dae);
            throw new EmployeeException("Unable to retrieve employee records");
        } catch (MappingException me) {
            log.error("Error mapping employee records", me);
            throw new EmployeeException("Unable to process employee records");
        }
    }

    @Override
    public Employee retrieveById(Long id) {
        try {
            EmployeeEntity employee = repository.findById(id)
                    .orElseThrow(() -> new EmployeeException(NOT_FOUND_MESSAGE + id));

            return mapper.map(employee, Employee.class);
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving employee record", dae);
            throw new EmployeeException("Unable to retrieve employee record");
        } catch (MappingException me) {
            log.error("Error mapping employee record", me);
            throw new EmployeeException("Unable to process employee record");
        }
    }

    @Override
    public Employee update(Long id, Employee employeeDetails) {
        try {
            EmployeeEntity existingEmployee = repository.findById(id)
                    .orElseThrow(() -> new EmployeeException(NOT_FOUND_MESSAGE + id));

            mapper.map(employeeDetails, existingEmployee);

            EmployeeEntity updatedEmployee = repository.save(existingEmployee);
            return mapper.map(updatedEmployee, Employee.class);
        } catch (DataAccessException dae) {
            log.error("Database error while updating employee with ID: {}", id, dae);
            throw new EmployeeException("Unable to update employee record");
        } catch (MappingException me) {
            log.error("Error mapping employee details for update: {}", employeeDetails, me);
            throw new EmployeeException("Failed to update employee data");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            EmployeeEntity employee = repository.findById(id)
                    .orElseThrow(() -> new EmployeeException(NOT_FOUND_MESSAGE + id));

            repository.delete(employee);
            log.info("Employee with ID {} deleted successfully", id);
        } catch (DataAccessException dae) {
            log.error("Database error while deleting employee with ID: {}", id, dae);
            throw new EmployeeException("Unable to delete employee record");
        }
    }
}
