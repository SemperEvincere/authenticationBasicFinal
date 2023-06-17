package com.bbva.authentication.infrastructure.controllers;

import com.bbva.authentication.application.useCases.IUserUseCase;
import com.bbva.authentication.infrastructure.controllers.request.PromotionRequest;
import com.bbva.authentication.infrastructure.controllers.request.UserCreateRequest;
import com.bbva.authentication.infrastructure.controllers.response.PromotedResponse;
import com.bbva.authentication.infrastructure.controllers.response.UserResponse;
import com.bbva.authentication.infrastructure.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class LoginController {
    private final IUserUseCase userService;

    @PostMapping(value = "/user/create")
    public ResponseEntity<UserEntity> create(UserCreateRequest userCreateRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.newUser(userCreateRequest));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User already exists")
                    .toString());
        }

    }

    @PostMapping(value = "/user/createAdmin")
    public ResponseEntity<UserEntity> createAdmin(UserCreateRequest userCreateRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.newAdmin(userCreateRequest));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User already exists")
                    .toString());
        }

    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<UserEntity> getByUserName(@PathVariable String username) {
        Optional<UserEntity> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound()
                        .build());
    }


    @GetMapping(value = "/user/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserEntity user) {
        UserResponse userDTO = UserResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRoles()
                        .toString())
                .build();
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(value = "/user/promotion")
    public ResponseEntity<?> promotion(@RequestBody PromotionRequest promotionRequest) {
        UserEntity user = userService.findByUsername(promotionRequest.getUsernamePromoted())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getRoles()
                .contains(promotionRequest.getRole())) {
            throw new IllegalArgumentException("User already has this role");
        }

        userService.promoteUser(user);
        PromotedResponse promotedResponse = PromotedResponse.builder()
                .usernamePromoted(promotionRequest.getUsernamePromoted())
                .role(String.valueOf(promotionRequest.getRole()))
                .build();
        return ResponseEntity.ok(promotedResponse);
    }
}
