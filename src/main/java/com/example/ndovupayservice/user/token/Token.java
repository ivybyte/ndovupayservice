package com.example.ndovupayservice.user.token;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token", schema = "js_core")
public class Token {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true,length = 5000)
    private String token;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    private UUID usrId;
}


