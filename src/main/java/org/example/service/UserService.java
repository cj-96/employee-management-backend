package org.example.service;

import org.example.dto.User;

import java.util.List;

public interface UserService {
    User persist(User user);

    List<User> retrieveAll();

    User retrieveById(Long id);

    User update(Long id, User userDetails);

    void delete(Long id);
}
