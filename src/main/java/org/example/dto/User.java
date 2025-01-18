package org.example.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Employee employee;
}
