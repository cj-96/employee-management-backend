package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String jobTitle;

    private Department department;

    private String profilePhoto;

    private String role;

    private List<Attendance> attendanceRecords;

    private List<LeaveRequest> leaveRequests;

    private List<Payroll> payrolls;

    private List<LeaveBalance> leaveBalances;
}
