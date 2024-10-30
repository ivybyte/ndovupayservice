package com.example.ndovupayservice.organisation;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class OrganisationDto {
    @NotNull(message = "Field Cannot be Empty") private String orgCounty;
    @NotNull(message = "Field Cannot be Empty") private String orgName;
    @NotNull(message = "Field Cannot be Empty") private String orgEmail;
    @NotNull(message = "Field Cannot be Empty") private String orgMobile;
    @NotNull(message = "Field Cannot be Empty") private String orgCertificateOfIncorporation;
    @NotNull(message = "Field Cannot be Empty") private String orgKraPin;
    @NotNull(message = "Field Cannot be Empty") private String orgTown;
    @NotNull(message = "Field Cannot be Empty") private String orgLocation;

    @NotNull(message = "Field Cannot be Empty") private String orgBusinessType;
    @NotNull(message = "Field Cannot be Empty") private String orgPostalAddress;


    @NotNull(message = "Field Cannot be Empty") private String usrFirstName;
    @NotNull(message = "Field Cannot be Empty") private String usrLastName;
    @NotNull(message = "Field Cannot be Empty") private String usrEmail;
    @NotNull(message = "Field Cannot be Empty") private String usrMobileNumber;
    @NotNull(message = "Field Cannot be Empty") private String usrEncryptedPassword;
    @NotNull(message = "Field Cannot be Empty") private String usrNationalId;
}
