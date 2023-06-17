package com.bbva.authentication.infrastructure.controllers.request;

import com.bbva.authentication.domain.models.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromotionRequest {

    private String usernamePromoted;
    private UserRole role;
}
