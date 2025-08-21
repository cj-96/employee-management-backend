package org.example.repository;

import org.example.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("""
            SELECT COUNT(e.id)
            FROM EmployeeEntity e
            WHERE e.deletedAt IS NULL
              AND e.id NOT IN (
                  SELECT lr.employee.id
                  FROM LeaveRequestEntity lr
                  WHERE lr.status = 'APPROVED'
                    AND CURRENT_DATE BETWEEN lr.startDate AND lr.endDate
              )
            """)
    Integer retrieveCountAtWork();

    @Query("""
            SELECT COUNT(DISTINCT e.id)
            FROM EmployeeEntity e
            JOIN e.leaveRequests lr
            WHERE e.deletedAt IS NULL
              AND lr.status = 'APPROVED'
              AND CURRENT_DATE BETWEEN lr.startDate AND lr.endDate
            """)
    Integer retrieveCountOnLeave();

    @Query("""
            SELECT COUNT(DISTINCT e.id)
            FROM EmployeeEntity e
            JOIN e.leaveRequests lr
            WHERE e.deletedAt IS NULL
              AND lr.status = 'PENDING'
              AND CURRENT_DATE BETWEEN lr.startDate AND lr.endDate
            """)
    Integer retrieveCountPending();

    @Query("""
            SELECT COUNT(e.id)
            FROM EmployeeEntity e
            WHERE e.deletedAt IS NULL
            """)
    Integer retrieveCount();

    @Query("""
            SELECT e.department.name AS department, COUNT(e.id) AS count
            FROM EmployeeEntity e
            WHERE e.deletedAt IS NULL
            GROUP BY e.department.name
            """)
    Map<String, Integer> getEmployeeCountPerDepartment();
}
