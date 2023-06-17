package com.bbva.authentication.infrastructure.controllers.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromotedResponse {

    private String usernamePromoted;
    private String role;
}
