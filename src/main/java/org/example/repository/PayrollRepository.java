package org.example.repository;

import org.example.entity.AttendanceEntity;
import org.example.entity.PayrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<PayrollEntity,Long> {
}
