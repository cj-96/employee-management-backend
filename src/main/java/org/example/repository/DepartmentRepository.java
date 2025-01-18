package org.example.repository;

import org.example.entity.AttendanceEntity;
import org.example.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long> {
}
