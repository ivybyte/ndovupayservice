package com.example.ndovupayservice.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
//    private UUID usrId;
    private String usrFirstname;
    private String usrLastname;
    @NotNull(message = "Email Cannot be null")
    private String usrEmail;
    private String usrMobileNumber; //do not update phone number
    private String usrNationalId;
    private UsrStatus usrStatus;
    private String password;
    private String usrLogo;
    private String layer;
    private int start;
    private int limit;
    private String sortColumn;
    private String usrOrgCode;
}
