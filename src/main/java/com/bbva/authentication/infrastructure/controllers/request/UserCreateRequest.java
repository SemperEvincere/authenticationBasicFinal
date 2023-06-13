package com.bbva.authentication.infrastructure.controllers.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {
	
	private String username;
	private String password;
	private String passwordConfirm;
}
