package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.User;
import org.example.entity.UserEntity;
import org.example.exception.UserException;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "User not found with ID: ";

    @Override
    public User persist(User user) {
        try {
            UserEntity save = repository.save(mapper.map(user, UserEntity.class));
            return mapper.map(save, User.class);
        } catch (DataAccessException e) {
            log.error("Failed to persist user: {}", user, e);
            throw new UserException("Failed to save user to database");
        } catch (MappingException e) {
            log.error("Error mapping user object: {}", user, e);
            throw new UserException("Failed to convert user data");
        }
    }

    @Override
    public List<User> retrieveAll() {
        try {
            List<User> list = new ArrayList<>();
            List<UserEntity> all = repository.findAll();

            if (all.isEmpty()) {
                throw new UserException("Users not found");
            }
            all.forEach(item -> list.add(mapper.map(item, User.class)));

            return list;
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving user records", dae);
            throw new UserException("Unable to retrieve user records");
        } catch (MappingException me) {
            log.error("Error mapping user records", me);
            throw new UserException("Unable to process user records");
        }
    }

    @Override
    public User retrieveById(Long id) {
        try {
            UserEntity user = repository.findById(id)
                    .orElseThrow(() -> new UserException(NOT_FOUND_MESSAGE + id));

            return mapper.map(user, User.class);
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving user record", dae);
            throw new UserException("Unable to retrieve user record");
        } catch (MappingException me) {
            log.error("Error mapping user record", me);
            throw new UserException("Unable to process user record");
        }
    }

    @Override
    public User update(Long id, User userDetails) {
        try {
            UserEntity existingUser = repository.findById(id)
                    .orElseThrow(() -> new UserException(NOT_FOUND_MESSAGE + id));

            mapper.map(userDetails, existingUser);

            UserEntity updatedUser = repository.save(existingUser);
            return mapper.map(updatedUser, User.class);
        } catch (DataAccessException dae) {
            log.error("Database error while updating user with ID: {}", id, dae);
            throw new UserException("Unable to update user record");
        } catch (MappingException me) {
            log.error("Error mapping user details for update: {}", userDetails, me);
            throw new UserException("Failed to update user data");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            UserEntity user = repository.findById(id)
                    .orElseThrow(() -> new UserException(NOT_FOUND_MESSAGE + id));

            repository.delete(user);
            log.info("User with ID {} deleted successfully", id);
        } catch (DataAccessException dae) {
            log.error("Database error while deleting user with ID: {}", id, dae);
            throw new UserException("Unable to delete user record");
        }
    }
}
