package org.example.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.Task;
import org.example.entity.TaskEntity;
import org.example.exception.TaskException;
import org.example.repository.TaskRepository;
import org.example.service.TaskService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final ModelMapper mapper;
    private final EntityManager entityManager;
    private static final String NOT_FOUND_MESSAGE = "Task not found with ID: ";

    @Override
    public Task persist(Task task) {
        try {
            TaskEntity save = repository.save(mapper.map(task, TaskEntity.class));
            return mapper.map(save, Task.class);
        } catch (DataAccessException e) {
            log.error("Failed to persist payroll: {}", task, e);
            throw new TaskException("Failed to save payroll to database");
        } catch (MappingException e) {
            log.error("Error mapping payroll object: {}", task, e);
            throw new TaskException("Failed to convert payroll data");
        }
    }

    @Override
    public List<Task> retrieveAll() {
        try{
            List<Task> list = new ArrayList<>();
            List<TaskEntity> all = repository.findAll();

            if(all.isEmpty()){
                throw new TaskException("Tasks not found");
            }
            all.forEach(item -> list.add(mapper.map(item, Task.class)));

            return list;
        } catch(DataAccessException e){
            log.error("Database error while retrieving task records",e);
            throw new TaskException("Unable to retrieve task records");
        } catch (MappingException me) {
            log.error("Error mapping task records", me);
            throw new TaskException("Unable to process task records");
        }
    }

    @Override
    public Task retrieveById(Long id) {
        try{
            TaskEntity byId = repository.findById(id)
                    .orElseThrow(() -> new TaskException(NOT_FOUND_MESSAGE + id));

            return mapper.map(byId, Task.class);
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving task record", dae);
            throw new TaskException("Unable to retrieve task record");
        } catch (MappingException me) {
            log.error("Error mapping task record", me);
            throw new TaskException("Unable to process task record");
        }
    }

    @Override
    public Task update(Long id, Task task) {
        try {
            TaskEntity existingTask = repository.findById(id)
                    .orElseThrow(() -> new TaskException(NOT_FOUND_MESSAGE + id));

            mapper.map(task, existingTask);

            TaskEntity updatedTask = repository.save(existingTask);
            return mapper.map(updatedTask, Task.class);
        } catch (DataAccessException dae) {
            log.error("Database error while updating payroll with ID: {}", id, dae);
            throw new TaskException("Unable to update payroll record");
        } catch (MappingException me) {
            log.error("Error mapping payroll details for update: {}", task, me);
            throw new TaskException("Failed to update payroll data");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            TaskEntity task = repository.findById(id)
                    .orElseThrow(() -> new TaskException(NOT_FOUND_MESSAGE + id));

            repository.delete(task);
            log.info("Payroll with ID {} deleted successfully", id);
        } catch (DataAccessException dae) {
            log.error("Database error while deleting payroll with ID: {}", id, dae);
            throw new TaskException("Unable to delete payroll record");
        }
    }

    @Override
    public Integer retrieveCountByEmployee(Long id, String status, String priority) {
        String query = "SELECT assignedTo.id, COUNT(t.id)"+
                        "FROM TaskEntity t"+
                        "JOIN t.assignedTo assignedTo"+
                        "WHERE deletedAt IS null";

        if (!"all".equalsIgnoreCase(status)) {
            query += "AND t.status = :status ";
        }
        if (!"all".equalsIgnoreCase(priority)) {
            query += "AND t.priority = :priority ";
        }

        TypedQuery<Integer> typedQuery = entityManager.createQuery(query, Integer.class);

        if (!"all".equalsIgnoreCase(status)) {
            typedQuery.setParameter("status", status);
        }
        if (!"all".equalsIgnoreCase(priority)) {
            typedQuery.setParameter("priority", priority);
        }
        return typedQuery.getSingleResult();
//        return null;
    }

    @Override
    public Integer retrieveCountByDepartment(Long id, String status, String priority) {
        String query = "SELECT createdBy.department.id, COUNT(t.id)"+
                "FROM TaskEntity t"+
                "JOIN t.createdBy createdBy"+
                "WHERE deletedAt IS null";

        if (!"all".equalsIgnoreCase(status)) {
            query += "AND t.status = :status ";
        }
        if (!"all".equalsIgnoreCase(priority)) {
            query += "AND t.priority = :priority ";
        }

        TypedQuery<Integer> typedQuery = entityManager.createQuery(query, Integer.class);

        if (!"all".equalsIgnoreCase(status)) {
            typedQuery.setParameter("status", status);
        }
        if (!"all".equalsIgnoreCase(priority)) {
            typedQuery.setParameter("priority", priority);
        }
        return typedQuery.getSingleResult();
//        return null;
    }

    @Override
    public List<Object[]> retrieveCountForAllDepartments(String status, String priority) {
        String query = "SELECT createdBy.department.name, COUNT(t.id)"+
                        "FROM TaskEntity t"+
                        "JOIN t.createdBy createdBy"+
                        "WHERE deletedAt IS null";

        if (!"all".equalsIgnoreCase(status)) {
            query += "AND t.status = :status ";
        }
        if (!"all".equalsIgnoreCase(priority)) {
            query += "AND t.priority = :priority ";
        }
        query += "GROUP BY createdBy.department.name";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query, Object[].class);

        if (!"all".equalsIgnoreCase(status)) {
            typedQuery.setParameter("status", status);
        }
        if (!"all".equalsIgnoreCase(priority)) {
            typedQuery.setParameter("priority", priority);
        }

        return typedQuery.getResultList();
//        return null;

    }

    @Override
    public List<Task> retrieveByEmployee(Long id, String status, String priority) {
        List<Task> list = new ArrayList<>();

        String query = "SELECT t FROM TaskEntity t " +
                "JOIN t.assignedTo assignedTo " +
                "WHERE t.deletedAt IS NULL AND assignedTo.id = :id ";
        if (!"all".equalsIgnoreCase(status)) {
            query += "AND t.status = :status ";
        }
        if (!"all".equalsIgnoreCase(priority)) {
            query += "AND t.priority = :priority ";
        }

        TypedQuery<TaskEntity> typedQuery = entityManager.createQuery(query, TaskEntity.class);
        typedQuery.setParameter("id", id);

        if (!"all".equalsIgnoreCase(status)) {
            typedQuery.setParameter("status", status);
        }
        if (!"all".equalsIgnoreCase(priority)) {
            typedQuery.setParameter("priority", priority);
        }

        List<TaskEntity> resultList = typedQuery.getResultList();

        if(resultList.isEmpty()){
            throw new TaskException("Tasks not found");
        }
        resultList.forEach(item -> list.add(mapper.map(item, Task.class)));

        return list;
//        return null;

    }


    @Override
    public List<Task> retrieveByDepartment(Long id, String status, String priority) {
        List<Task> list = new ArrayList<>();

        String query = "SELECT t FROM TaskEntity t " +
                "JOIN t.createdBy createdBy " +
                "WHERE t.deletedAt IS NULL AND createdBy.department.id = :id ";
        if (!"all".equalsIgnoreCase(status)) {
            query += "AND t.status = :status ";
        }
        if (!"all".equalsIgnoreCase(priority)) {
            query += "AND t.priority = :priority ";
        }

        TypedQuery<TaskEntity> typedQuery = entityManager.createQuery(query, TaskEntity.class);
        typedQuery.setParameter("id", id);

        if (!"all".equalsIgnoreCase(status)) {
            typedQuery.setParameter("status", status);
        }
        if (!"all".equalsIgnoreCase(priority)) {
            typedQuery.setParameter("priority", priority);
        }

        List<TaskEntity> resultList = typedQuery.getResultList();

        if(resultList.isEmpty()){
            throw new TaskException("Tasks not found");
        }
        resultList.forEach(item -> list.add(mapper.map(item, Task.class)));

        return list;
//        return null;
    }
}
