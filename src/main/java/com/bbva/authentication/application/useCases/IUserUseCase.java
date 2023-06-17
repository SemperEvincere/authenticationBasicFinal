package com.bbva.authentication.application.useCases;

import com.bbva.authentication.infrastructure.controllers.request.UserCreateRequest;
import com.bbva.authentication.infrastructure.entities.UserEntity;

import java.util.Optional;

public interface IUserUseCase {

    UserEntity newUser(UserCreateRequest user);

    Optional<UserEntity> findByUsername(String username);

    Boolean authenticateUser(String username, String password);

    void promoteUser(UserEntity user);

    UserEntity newAdmin(UserCreateRequest userCreateRequest);
}
