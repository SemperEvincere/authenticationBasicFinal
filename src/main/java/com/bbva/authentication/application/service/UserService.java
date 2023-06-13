package com.bbva.authentication.application.service;

import com.bbva.authentication.application.repository.IUserRepository;
import com.bbva.authentication.application.useCases.IUserUseCase;
import com.bbva.authentication.domain.models.enums.UserRole;
import com.bbva.authentication.infrastructure.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserUseCase {
	private final PasswordEncoder passwordEncoder;
	private final IUserRepository userRepository;
	
	public UserEntity newUser(UserEntity user) {
		// Genera una sal aleatoria para el usuario
		String salt = BCrypt.gensalt();
		
		// Crea el hash de la contraseña con la sal generada utilizando BCryptPasswordEncoder
//		String hashedPassword = passwordEncoder.encode(user.getPassword() + salt);
		String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
		
		// Almacena la sal y el hash en la base de datos
		user.setPassword(hashedPassword);
		user.setSalt(salt);
		user.setRoles(Set.of(UserRole.USER));
		
		return userRepository.save(user);
	}
	
	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public Boolean authenticateUser(String username, String password) {
		return userRepository.authenticateUser(username, password);
	}
}
