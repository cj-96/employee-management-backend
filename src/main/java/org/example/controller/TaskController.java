package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Payroll;
import org.example.dto.SuccessResponse;
import org.example.dto.Task;
import org.example.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    private final TaskService service;
    private static final String SUCCESS_STATUS = "success";

    @PostMapping
    public ResponseEntity<SuccessResponse> persist(@RequestBody Task task) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.persist(task))
                .build();

        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> retrieveAll() {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveAll())
                .build();

        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> retrieveById(@PathVariable Long id) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveById(id))
                .build();

        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<SuccessResponse> retrieveByEmployee(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {

        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveByEmployee(id, status, priority))
                .build();

        return ResponseEntity.ok(successResponse);
    }

    // 2. Get task count for a department with filters
    @GetMapping("/department/{id}")
    public ResponseEntity<SuccessResponse> retrieveByDepartment(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {

        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveByDepartment(id, status, priority))
                .build();

        return ResponseEntity.ok(successResponse);
    }

    @GetMapping("/count/employee/{id}")
    public ResponseEntity<SuccessResponse> retrieveCountByEmployee(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {

        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveCountByEmployee(id, status, priority))
                .build();

        return ResponseEntity.ok(successResponse);
    }

    // 2. Get task count for a department with filters
    @GetMapping("/count/department/{id}")
    public ResponseEntity<SuccessResponse> retrieveCountByDepartment(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {

        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveCountByDepartment(id, status, priority))
                .build();

        return ResponseEntity.ok(successResponse);
    }

    // 3. Get task count for all departments with filters
    @GetMapping("/count/all-departments")
    public ResponseEntity<SuccessResponse> retrieveCountForAllDepartments(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {

        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveCountForAllDepartments(status, priority))
                .build();

        return ResponseEntity.ok(successResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable Long id, @RequestBody Task task) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.update(id, task))
                .build();

        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id) {
        service.delete(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

}
