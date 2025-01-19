package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Attendance;
import org.example.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService service;

    // Create Attendance
    @PostMapping
    public ResponseEntity<Attendance> persist(@RequestBody Attendance attendance) {
        Attendance createdAttendance = service.persist(attendance);
        return ResponseEntity.ok(createdAttendance);
    }

    // Get All Attendance Records
    @GetMapping
    public ResponseEntity<List<Attendance>> retrieveAll() {
        List<Attendance> attendanceList = service.retrieveAll();
        return ResponseEntity.ok(attendanceList);
    }

    // Get Attendance by ID
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> retrieveById(@PathVariable Long id) {
        Attendance attendance = service.retrieveById(id);
        return ResponseEntity.ok(attendance);
    }

    // Update Attendance
    @PutMapping("/{id}")
    public ResponseEntity<Attendance> update(@PathVariable Long id, @RequestBody Attendance attendanceDetails) {
        Attendance updatedAttendance = service.update(id, attendanceDetails);
        return ResponseEntity.ok(updatedAttendance);
    }

    // Delete Attendance
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Attendance record deleted successfully.");
    }
}
