package com.example.ndovupayservice.organisation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Entity
@Builder
@RequiredArgsConstructor
@Table(name = "organization", schema = "js_core", catalog = "entrust_corporate")
@AllArgsConstructor
public class Organisation {
    @Id
    @GeneratedValue

    private UUID orgUid;



//    @Column(name = "org_id", nullable = false)
//    private Long orgId;
    @Column(name = "org_id", insertable = false, updatable = false) // Trigger populates this, so don't insert
    @Generated(GenerationTime.ALWAYS) // Always retrieve the latest value from the DB
    private Long orgId;

    @Column(name = "org_name", nullable = false, length = 250)
    private String orgName;

    @Column(name = "org_email", nullable = false, length = 250)
    private String orgEmail;

    @Column(name = "org_mobile", nullable = false, length = 50)
    private String orgMobile;

    @Column(name = "org_telephone", length = 50)
    private String orgTelephone;

    @Column(name = "org_location", length = 250)
    private String orgLocation;

    @Column(name = "org_postal_address", length = 250)
    private String orgPostalAddress;

    @Column(name = "org_kra_pin", nullable = false, length = 50)
    private String orgKraPin;

    @Column(name = "org_certificate_of_incorporation", length = 250)
    private String orgCertificateOfIncorporation;

    @Column(name = "org_profile_pic", length = 250)
    private String orgProfilePic;

    @Column(name = "org_website", length = 250)
    private String orgWebsite;

    @Column(name = "org_business_type", length = 100)
    private String orgBusinessType;

    @Column(name = "org_services_offered", length = 50)
    private String orgServicesOffered;

    @Column(name = "org_description", length = 250)
    private String orgDescription;

    @Column(name = "org_created_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime orgCreatedTime;

    @Column(name = "org_created_by")
    private UUID orgCreatedBy;

    @Column(name = "org_updated_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime orgUpdatedTime;

    @Column(name = "org_update_by")
    private Integer orgUpdateBy;

    @Column(name = "org_county", length = 250)
    private String orgCounty;

    @Column(name = "org_town", length = 250)
    private String orgTown;

//    @Column(name = "org_code")
//    private String orgCode;

    @Column(name = "org_code", insertable = false, updatable = false) // Trigger populates this, so don't insert
    @Generated(GenerationTime.ALWAYS) // Always retrieve the latest value from the DB
    private String orgCode;

    @CreationTimestamp
    @Column(name = "org_created_date")
    private LocalDate orgCreatedDate;

    @Column(name = "org_updated_by")
    private Long orgUpdatedBy;

    @Column(name = "org_updated_date")
    private LocalDate orgUpdatedDate;

    @Column(name = "org_desc", columnDefinition = "TEXT")
    private String orgDesc;

    @Column(name = "org_status", columnDefinition = "TEXT")
    private String orgStatus;

    @Column(name = "org_disb_acc_no", columnDefinition = "TEXT")
    private String orgDisbAccNo;

    @Column(name = "org_disb_acc_url", columnDefinition = "TEXT")
    private String orgDisbAccUrl;

    @Column(name = "org_disb_paybill", columnDefinition = "TEXT")
    private String orgDisbPaybill;

    @Column(name = "org_disb_paybill_url", columnDefinition = "TEXT")
    private String orgDisbPaybillUrl;
}


