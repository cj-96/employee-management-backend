package org.example.repository;

import org.example.entity.AttendanceEntity;
import org.example.entity.LeaveBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalanceEntity,Long> {
}
