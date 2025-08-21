package org.example.service;

import org.example.dto.Task;

import java.util.List;

public interface TaskService {
    Task persist(Task task);

    List<Task> retrieveAll();

    Task retrieveById(Long id);

    Task update(Long id, Task task);

    void delete(Long id);

    Integer retrieveCountByEmployee(Long id, String status, String priority);

    Integer retrieveCountByDepartment(Long id, String status, String priority);

    List<Object[]> retrieveCountForAllDepartments(String status, String priority);

    List<Task> retrieveByEmployee(Long id, String status, String priority);

    List<Task> retrieveByDepartment(Long id, String status, String priority);
}
