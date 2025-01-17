package org.example.controller;

import org.example.dto.Attendance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Create Attendance
    @PostMapping
    public ResponseEntity<Attendance> persist(@RequestBody Attendance attendance) {
        Attendance createdAttendance = attendanceService.createAttendance(attendance);
        return ResponseEntity.ok(createdAttendance);
    }

    // Get All Attendance Records
    @GetMapping
    public ResponseEntity<List<Attendance>> retrieveAll() {
        List<Attendance> attendanceList = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendanceList);
    }

    // Get Attendance by ID
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> retrieveById(@PathVariable Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }

    // Update Attendance
    @PutMapping("/{id}")
    public ResponseEntity<Attendance> update(@PathVariable Long id, @RequestBody Attendance attendanceDetails) {
        Attendance updatedAttendance = attendanceService.updateAttendance(id, attendanceDetails);
        return ResponseEntity.ok(updatedAttendance);
    }

    // Delete Attendance
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok("Attendance record deleted successfully.");
    }
}
