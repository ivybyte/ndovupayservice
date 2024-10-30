package com.example.ndovupayservice.wallet;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CustomerWalletDto {
    private UUID cwOrgUid;

    private String cwBranch;


    private String cwCollection;


    private String cwDisbursement;


    private String cwOrganization;


    private LocalDateTime cwTransactionDate;


    private String cwTransactionType;


    private Integer cwWalletAcount;


    private UUID cwCreatedBy;


}
