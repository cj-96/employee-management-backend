package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeaveRequest {
    private Long id;

    private Employee employee;

    private LocalDate startDate;

    private LocalDate endDate;

    private String type;

    private String status;

    private String reason;
}
