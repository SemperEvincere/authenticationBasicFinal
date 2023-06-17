package com.bbva.authentication.application.repository;

import com.bbva.authentication.infrastructure.entities.UserEntity;

import java.util.Optional;

public interface IUserRepository {
    UserEntity save(UserEntity user);

    Optional<UserEntity> findByUsername(String username);

    Boolean authenticateUser(String username, String password);
}
