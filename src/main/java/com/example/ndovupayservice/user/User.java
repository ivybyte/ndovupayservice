package com.example.ndovupayservice.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stp_user", schema = "js_core")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID usrId;
    private String usrFirstname;
    private String usrLastname;

    @NotNull
    @Column(updatable = false, nullable = false)
    private String usrNationalId;

    private String usrMobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status Cannot be Null")
    private UsrStatus usrStatus;
    private String usrOtpStatus;
    private String usrLogo;

//    @Enumerated(EnumType.STRING)
//    @Column(updatable = false, nullable = false)
//    private Layers layer = Layers.TOP;

    //todo uncomment below
//    @NotNull
    private UUID brnId;
    //    @NotNull
    private UUID usrResellerId;
    private UUID usrAccId;


    @JsonIgnore
    private String usrEmailOtp;
    @JsonIgnore
    private String usrPhoneOtp;

    @Column(nullable = false)
    private String usrEmail;

    @JsonIgnore
    private String usrPhoneWithdrawOtp;

    @CreationTimestamp
    private LocalDateTime usrCreatedDate;
    private UUID usrCreatedBy;

    private Boolean usrChangePassword;

    private Date usrDob;

    @Transient
    private int usrAge;

    private String usrKraPin;

    private String usrKraPinFile;

    @JsonIgnore
    private String password;

    private String usrNationalIdFrontFile;

    private String usrNationalIdBackFile;

    private String usrPhysicalAddress;

    private String usrCounty;

    private String usrCountry;

    private String usrTown;

    @Column(name = "usr_org_code", length = 32)
    private String usrOrgCode;
    private UUID usrOrgId;

    @JsonIgnore
    private String usrSalt;
    @Builder.Default
    private Boolean usrIsOnboarded = false;

    private String usrDisableReason;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return usrNationalId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

