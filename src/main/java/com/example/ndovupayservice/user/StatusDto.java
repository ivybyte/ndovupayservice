package com.example.ndovupayservice.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class StatusDto {
    private String usrStatus;
}
