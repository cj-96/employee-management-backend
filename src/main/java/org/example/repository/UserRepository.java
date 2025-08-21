package org.example.repository;

import org.example.entity.AttendanceEntity;
import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<Object> findByUsername(String username);
}
