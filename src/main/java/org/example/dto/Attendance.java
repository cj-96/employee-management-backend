package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.entity.EmployeeEntity;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Attendance {
    private Employee employee;

    private LocalDate date;

    private String status;

    private String checkInTime;

    private String checkOutTime;
}
