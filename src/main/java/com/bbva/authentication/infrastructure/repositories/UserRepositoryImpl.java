package com.bbva.authentication.infrastructure.repositories;

import com.bbva.authentication.application.repository.IUserRepository;
import com.bbva.authentication.infrastructure.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final UserSpringRepository userSpringRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity save(UserEntity user) {
        return userSpringRepository.save(user);
    }


    public Boolean authenticateUser(String username, String password) {
        Optional<UserEntity> user = userSpringRepository.findByUsername(username);
        if (user.isEmpty()) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(password);
        System.out.println(hashedPassword);
        System.out.println(user.get()
                .getPassword());
        return hashedPassword.equals(user.get()
                .getPassword());
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userSpringRepository.findByUsername(username);
    }
}
