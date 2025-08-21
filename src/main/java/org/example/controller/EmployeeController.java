package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Employee;
import org.example.dto.SuccessResponse;
import org.example.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
@CrossOrigin()
public class EmployeeController {

    private final EmployeeService service;
    private static final String SUCCESS_STATUS = "success";

    @PostMapping
    public ResponseEntity<SuccessResponse> persist(@RequestBody Employee employee) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.persist(employee))
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

    @GetMapping("/count")
    public ResponseEntity<SuccessResponse> retrieveCount() {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveCount())
                .build();

        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/count/atWork")
    public ResponseEntity<SuccessResponse> retrieveCountOnLeave() {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveCountOnLeave())
                .build();

        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/count/onLeave")
    public ResponseEntity<SuccessResponse> retrieveCountAtWork() {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveCountAtWork())
                .build();

        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/count/pending")
    public ResponseEntity<SuccessResponse> retrieveCountPending() {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.retrieveCountPending())
                .build();

        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/count/allDepartments")
    public ResponseEntity<SuccessResponse> retrieveCountEachDepartment() {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.getEmployeeCountPerDepartment())
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

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.update(id, employeeDetails))
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
