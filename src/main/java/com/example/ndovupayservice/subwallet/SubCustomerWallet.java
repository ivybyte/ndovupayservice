package com.example.ndovupayservice.subwallet;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Table(schema = "js_wallet")
@Entity(name = "sub_customer_wallet")
@AllArgsConstructor

public class SubCustomerWallet {
    @Id
    @GeneratedValue

    private UUID subWalUId;

    @Column(name = "sub_wal_id")
    private Long subWalId;

    @Column(name = "sub_wal_currency", length = 255)
    private String subWalCurrency;

    @Column(name = "sub_wal_amount", precision = 38, scale = 0, nullable = false)
    private BigDecimal subWalAmount;

    @Column(name = "sub_wal_status", length = 255)
    private String subWalStatus;

    @Column(name = "sub_wal_desc", length = 255)
    private String subWalDesc;

    @Column(name = "sub_wal_created_at")
    private LocalDate subWalCreatedAt;

    @Column(name = "sub_wal_created_by")
    private Long subWalCreatedBy;

    @Column(name = "sub_wal_updated_by")
    private Long subWalUpdatedBy;

    @Column(name = "sub_wal_updated_at")
    private LocalDate subWalUpdatedAt;

    @Column(name = "sub_wal_acc_id", nullable = true)
    private Long subWalAccId;

    @Column(name = "sub_wal_type", length = 255, nullable = false)
    @Enumerated(EnumType.STRING)
    private SubWalletType subWalType;

    @Column(name = "sub_wal_bal_amount", precision = 38, scale = 0, nullable = false)
    private BigDecimal subWalBalAmount;

    @Column(name = "sub_wal_mpesa_number", length = 100)
    private String subWalMpesaNumber;

    @Column(name = "sub_wal_name", length = 255)
    private String subWalName;

    @Column(name = "sub_wal_type_code")
    private Integer subWalTypeCode;

    @Column(name = "sub_wal_code", length = 255)
    private String subWalCode;

    @Column(name = "sub_wal_url_confirmation", length = 255)
    private String subWalUrlConfirmation;

    @Column(name = "subWal_url_validation", length = 255)
    private String subWalUrlValidation;

    @Column(name = "sub_wal_brn_id", nullable = true)
    private Long subWalBrnId;

    @Column(name = "sub_wal_no", length = 255)
    private String subWalNo;

    @Column(name = "sub_wal_amount_before", precision = 38, scale = 0)
    private BigDecimal subWalAmountBefore;

    @Column(name = "sub_wal_amount_after", precision = 38, scale = 0)
    private BigDecimal subWalAmountAfter;

    @Column(name = "sub_wal_bal_amount_before", precision = 38, scale = 0)
    private BigDecimal subWalBalAmountBefore;

    @Column(name = "sub_wal_bal_amount_after", precision = 38, scale = 0)
    private BigDecimal subWalBalAmountAfter;

    @Column(name = "sub_wal_created_date", updatable = false)
    @CreationTimestamp

    private LocalDate subWalCreatedDate;

    @Column(name = "sub_wal_created_time", updatable = false)
    @CreationTimestamp
    private LocalDateTime subWalCreatedTime;

    @Column(name = "sub_wal_updated_date", insertable = false)
    private LocalDate subWalUpdatedDate;

    @Column(name = "sub_wal_updated_time")
    private LocalDateTime subWalUpdatedTime;

    @Column(name = "sub_wal_org_uid", nullable = false)
    private UUID subWalOrgUid;

    @Column(name = "sub_waluid_id", nullable = false)
    private UUID subWalIdId;


}

