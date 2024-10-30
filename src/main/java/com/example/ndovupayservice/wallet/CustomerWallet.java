package com.example.ndovupayservice.wallet;

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
@Entity(name = "customer_wallet")
@AllArgsConstructor
public class CustomerWallet {
    @Id
    @GeneratedValue

    private UUID walUId;

    @Column(name = "wal_id")
    private Long walId;

    @Column(name = "wal_currency", length = 255)
    private String walCurrency;

    @Column(name = "wal_amount", precision = 38, scale = 0, nullable = false)
    private BigDecimal walAmount;

    @Column(name = "wal_status", length = 255)
    private String walStatus;

    @Column(name = "wal_desc", length = 255)
    private String walDesc;

    @Column(name = "wal_created_at")
    private LocalDate walCreatedAt;

    @Column(name = "wal_created_by")
    private Long walCreatedBy;

    @Column(name = "wal_updated_by")
    private Long walUpdatedBy;

    @Column(name = "wal_updated_at")
    private LocalDate walUpdatedAt;

    @Column(name = "wal_acc_id", nullable = true)
    private Long walAccId;

    @Column(name = "wal_type", length = 255, nullable = false)
    @Enumerated(EnumType.STRING)
    private WalletType walType;

    @Column(name = "wal_bal_amount", precision = 38, scale = 0, nullable = false)
    private BigDecimal walBalAmount;

    @Column(name = "wal_mpesa_number", length = 100)
    private String walMpesaNumber;

    @Column(name = "wal_name", length = 255)
    private String walName;

    @Column(name = "wal_type_code")
    private Integer walTypeCode;

    @Column(name = "wal_code", length = 255)
    private String walCode;

    @Column(name = "wal_url_confirmation", length = 255)
    private String walUrlConfirmation;

    @Column(name = "wal_url_validation", length = 255)
    private String walUrlValidation;

    @Column(name = "wal_brn_id", nullable = true)
    private Long walBrnId;

    @Column(name = "wal_no", length = 255)
    private String walNo;

    @Column(name = "wal_amount_before", precision = 38, scale = 0)
    private BigDecimal walAmountBefore;

    @Column(name = "wal_amount_after", precision = 38, scale = 0)
    private BigDecimal walAmountAfter;

    @Column(name = "wal_bal_amount_before", precision = 38, scale = 0)
    private BigDecimal walBalAmountBefore;

    @Column(name = "wal_bal_amount_after", precision = 38, scale = 0)
    private BigDecimal walBalAmountAfter;

    @Column(name = "wal_created_date", updatable = false)
    @CreationTimestamp

    private LocalDate walCreatedDate;

    @Column(name = "wal_created_time", updatable = false)
    @CreationTimestamp
    private LocalDateTime walCreatedTime;

    @Column(name = "wal_updated_date", insertable = false)
    private LocalDate walUpdatedDate;

    @Column(name = "wal_updated_time")
    private LocalDateTime walUpdatedTime;

    @Column(name = "wal_org_uid", nullable = false)
    private UUID walOrgUid;

}


