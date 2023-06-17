package com.bbva.authentication.infrastructure.controllers.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private String username;
    private String password;
    private String role;
}
