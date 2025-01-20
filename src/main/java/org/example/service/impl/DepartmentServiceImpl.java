package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.Department;
import org.example.entity.DepartmentEntity;
import org.example.exception.DepartmentException;
import org.example.repository.DepartmentRepository;
import org.example.service.DepartmentService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final ModelMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "Department not found with ID: ";

    @Override
    public Department persist(Department department) {
        try {
            DepartmentEntity save = repository.save(mapper.map(department, DepartmentEntity.class));
            return mapper.map(save, Department.class);
        } catch (DataAccessException e) {
            log.error("Failed to persist department: {}", department, e);
            throw new DepartmentException("Failed to save department to database");
        } catch (MappingException e) {
            log.error("Error mapping department object: {}", department, e);
            throw new DepartmentException("Failed to convert department data");
        }
    }

    @Override
    public List<Department> retrieveAll() {
        try {
            List<Department> list = new ArrayList<>();
            List<DepartmentEntity> all = repository.findAll();

            if (all.isEmpty()) {
                throw new DepartmentException("Departments Not Found");
            }
            all.forEach(item -> list.add(mapper.map(item, Department.class)));

            return list;
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving department records", dae);
            throw new DepartmentException("Unable to retrieve department records");
        } catch (MappingException me) {
            log.error("Error mapping department records", me);
            throw new DepartmentException("Unable to process department records");
        }
    }

    @Override
    public Department retrieveById(Long id) {
        try {
            DepartmentEntity department = repository.findById(id)
                    .orElseThrow(() -> new DepartmentException(NOT_FOUND_MESSAGE + id));

            return mapper.map(department, Department.class);
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving department record", dae);
            throw new DepartmentException("Unable to retrieve department record");
        } catch (MappingException me) {
            log.error("Error mapping department record", me);
            throw new DepartmentException("Unable to process department record");
        }
    }

    @Override
    public Department update(Long id, Department departmentDetails) {
        try {
            DepartmentEntity existingDepartment = repository.findById(id)
                    .orElseThrow(() -> new DepartmentException(NOT_FOUND_MESSAGE + id));

            mapper.map(departmentDetails, existingDepartment);

            DepartmentEntity updatedDepartment = repository.save(existingDepartment);
            return mapper.map(updatedDepartment, Department.class);
        } catch (DataAccessException dae) {
            log.error("Database error while updating department with ID: {}", id, dae);
            throw new DepartmentException("Unable to update department record");
        } catch (MappingException me) {
            log.error("Error mapping department details for update: {}", departmentDetails, me);
            throw new DepartmentException("Failed to update department data");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            DepartmentEntity department = repository.findById(id)
                    .orElseThrow(() -> new DepartmentException(NOT_FOUND_MESSAGE + id));

            repository.delete(department);
            log.info("Department with ID {} deleted successfully", id);
        } catch (DataAccessException dae) {
            log.error("Database error while deleting department with ID: {}", id, dae);
            throw new DepartmentException("Unable to delete department record");
        }
    }
}
