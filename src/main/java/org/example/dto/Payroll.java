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
public class Payroll {
    private Long id;

    private Employee employee;

    private int month;

    private int year;

    private double basicSalary;

    private double deductions;

    private double netSalary;

    private LocalDate generatedDate;
}
