package com.example.ndovupayservice.user;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@Setter
@RequiredArgsConstructor
public class DraftSecret {
//    @NotNull(message = "Field Cannot be null")
//    private UUID usrId;
    @NotNull(message = "Field Cannot be null")
    private String usrOrgCode;
    @NotNull(message = "Field Cannot be null")
    private String userEmail;
    @NotNull(message = "Field Cannot be null")
    private String usrSecret;
}
