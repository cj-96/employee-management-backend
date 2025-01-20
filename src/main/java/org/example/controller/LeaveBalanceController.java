package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.LeaveBalance;
import org.example.dto.SuccessResponse;
import org.example.service.LeaveBalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave-balance")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LeaveBalanceController {

    private final LeaveBalanceService service;
    private final String SUCCESS_STATUS = "success";

    @PostMapping
    public ResponseEntity<SuccessResponse> persist(@RequestBody LeaveBalance leaveBalance) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.persist(leaveBalance))
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

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable Long id, @RequestBody LeaveBalance leaveBalanceDetails) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(SUCCESS_STATUS)
                .data(service.update(id, leaveBalanceDetails))
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
