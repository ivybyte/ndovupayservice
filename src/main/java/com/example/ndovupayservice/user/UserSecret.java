package com.example.ndovupayservice.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@Setter
@RequiredArgsConstructor
public class UserSecret {
    @NotNull(message = "Field Cannot be null")
    private String userSecret;
}
