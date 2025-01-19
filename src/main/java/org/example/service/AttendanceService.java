package org.example.service;

import org.example.dto.Attendance;

import java.util.List;

public interface AttendanceService {
    Attendance persist(Attendance attendance);

    List<Attendance> retrieveAll();

    Attendance retrieveById(Long id);

    Attendance update(Long id, Attendance attendanceDetails);

    void delete(Long id);
}
