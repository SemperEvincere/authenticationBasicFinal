package com.bbva.authentication.infrastructure.controllers;

import com.bbva.authentication.application.useCases.IUserUseCase;
import com.bbva.authentication.infrastructure.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class LoginController {
	private final IUserUseCase userService;
	
	@PostMapping(value = "/user/create")
	public ResponseEntity<UserEntity> create(@RequestParam String username, @RequestParam String password) {
		UserEntity user = UserEntity.builder()
		                            .username(username)
		                            .password(password)
		                            .build();
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
			                     .body(userService.newUser(user));
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
	
	@PostMapping(value = "/user/authenticate-basic")
	public ResponseEntity<Boolean> authenticate(@RequestParam String username, @RequestParam String password) {
		Boolean authenticated = userService.authenticateUser(username, password);
		return ResponseEntity.ok(authenticated);
	}
	
	@PostMapping(value = "/user/authenticate-form")
	public ResponseEntity<?> authenticateForm(@RequestParam String username, @RequestParam String password) {
		if (userService.authenticateUser(username, password)) {
			return ResponseEntity.ok("home");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();
		}
	}
}
