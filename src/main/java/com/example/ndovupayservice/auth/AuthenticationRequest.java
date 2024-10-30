package com.example.ndovupayservice.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotNull(message = "Field Cannot be null")
    private String usrOrgCode;
    @NotNull(message = "Field Cannot be null")
    private String userEmail;
    @NotNull(message = "Field Cannot be null")
    private String password;

    private     String usrSecret;

}
