package com.bbva.authentication.infrastructure.controllers.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreated {

    private String username;
    private Set<String> roles;
}
