package com.example.ndovupayservice.wallet;


import com.example.ndovupayservice.organisation.Organisation;
import com.example.ndovupayservice.utils.StandardJsonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerWalletService {

    private final CustomerWalletRepository customerWalletRepository;

    public void createDefultOrgWallet(Organisation org) {
        CustomerWallet customerWallet = CustomerWallet.builder()
//                .walId(org.getOrgUid())
                .walCurrency("KES")
                .walAmount(BigDecimal.ZERO)
                .walStatus("ACTIVE")
                .walDesc(org.getOrgDesc()+ "Wallet")
                .walCreatedAt(LocalDate.now())
//                .walCreatedBy(customerWalletDto.getCwCreatedBy())

//                .walUpdatedBy(customerWalletDto.getCwCreatedBy())
                .walUpdatedAt(LocalDate.now())

//                .walAccId(customerWalletDto.getCwCreatedBy())
                .walType(WalletType.COLLECTION)
                .walBalAmount(new BigDecimal(0))
                .walMpesaNumber(org.getOrgMobile())

                .walName(org.getOrgDesc()+ "Wallet")
                .walTypeCode(1)
                .walCode(org.getOrgCode()+"1")

//                .walUrlConfirmation(customerWalletDto.getCwCreatedBy())
//                .walUrlValidation(customerWalletDto.getCwCreatedBy())
//                .walBrnId(customerWalletDto.getCwCreatedBy())

                .walNo(org.getOrgCode()+"1")
                .walAmountBefore(new BigDecimal(0))
                .walAmountAfter(new BigDecimal(0))

                .walBalAmountBefore(new BigDecimal(0))
                .walBalAmountAfter(new BigDecimal(0))
                .walCreatedDate(LocalDate.now())
                .walCreatedTime(LocalDateTime.now())
                .walUpdatedDate(LocalDate.now())
                .walUpdatedTime(LocalDateTime.now())
                .walOrgUid(org.getOrgUid())
                .build();


        customerWalletRepository.saveAndFlush(customerWallet);
    }
}
