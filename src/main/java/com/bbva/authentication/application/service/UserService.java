package com.bbva.authentication.application.service;

import com.bbva.authentication.application.repository.IUserRepository;
import com.bbva.authentication.application.useCases.IUserUseCase;
import com.bbva.authentication.domain.models.enums.UserRole;
import com.bbva.authentication.infrastructure.controllers.request.UserCreateRequest;
import com.bbva.authentication.infrastructure.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserUseCase {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;

    public UserEntity newUser(UserCreateRequest user) {
        if (userRepository.findByUsername(user.getUsername())
                .isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        if (!user.getPassword()
                .contentEquals(user.getPasswordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        UserEntity newUser = UserEntity.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Set.of(UserRole.USER))
                .build();
        return userRepository.save(newUser);

    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean authenticateUser(String username, String password) {
        return userRepository.authenticateUser(username, password);
    }

    @Override
    public void promoteUser(UserEntity user) {
        user.getRoles()
                .clear();
        user.getRoles()
                .add(UserRole.PROMOTED);
        userRepository.save(user);
    }

    @Override
    public UserEntity newAdmin(UserCreateRequest userCreateRequest) {
        if (userRepository.findByUsername(userCreateRequest.getUsername())
                .isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        if (!userCreateRequest.getPassword()
                .contentEquals(userCreateRequest.getPasswordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        UserEntity newUser = UserEntity.builder()
                .username(userCreateRequest.getUsername())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .roles(Set.of(UserRole.ADMIN))
                .build();
        return userRepository.save(newUser);
    }
}
