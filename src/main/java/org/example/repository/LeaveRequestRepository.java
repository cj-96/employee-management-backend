package org.example.repository;

import org.example.entity.AttendanceEntity;
import org.example.entity.LeaveRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequestEntity,Long> {
}
