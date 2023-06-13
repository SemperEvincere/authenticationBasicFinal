package com.bbva.authentication.infrastructure.controllers.mappers;

import com.bbva.authentication.infrastructure.controllers.response.UserCreated;
import com.bbva.authentication.infrastructure.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
	
	public UserCreated toUserCreated(UserEntity userEntity) {
		return UserCreated.builder()
		                  .username(userEntity.getUsername())
		                  .roles(userEntity.getRoles()
		                                   .stream()
		                                   .map(Enum::name)
		                                   .collect(Collectors.toSet()))
		                  .build();
	}
}
