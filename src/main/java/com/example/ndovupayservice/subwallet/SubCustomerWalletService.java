package com.example.ndovupayservice.subwallet;

import com.example.ndovupayservice.wallet.CustomerWallet;
import com.example.ndovupayservice.subwallet.SubWalletType;
import com.example.ndovupayservice.organisation.Organisation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.ndovupayservice.subwallet.SubCustomerWallet.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubCustomerWalletService {
    private final SubCustomerWalletRepository subCustomerWalletRepository;

    public void createSubOrgWallet(CustomerWallet name, Organisation org) {
        SubCustomerWalletRepository subCustomerWalletRepository = (SubCustomerWalletRepository) builder()


                .subWalOrgUid(org.getOrgUid())
                .subWalIdId(name.getWalUId())
                .subWalAmount(BigDecimal.ZERO)
                .subWalCurrency("KES")
                .subWalStatus("ACTIVE")
                .subWalDesc(org.getOrgDesc() + "Wallet")
                .subWalCreatedAt(LocalDate.now())
                .subWalUpdatedAt(LocalDate.now())
                .subWalType(SubWalletType.COLLECTION)
                .subWalBalAmount(new BigDecimal(0))
                .subWalMpesaNumber(org.getOrgMobile())
                .subWalName(org.getOrgDesc() + "Wallet")
                .subWalTypeCode(1)
                .subWalCode(org.getOrgCode() + "1")
                .subWalNo(org.getOrgCode() + "1")
                .subWalAmountBefore(new BigDecimal(0))
                .subWalAmountAfter(new BigDecimal(0))
                .subWalBalAmountBefore(new BigDecimal(0))
                .subWalBalAmountAfter(new BigDecimal(0))
                .subWalCreatedDate(LocalDate.now())
                .subWalCreatedTime(LocalDateTime.now())
                .subWalUpdatedDate(LocalDate.now())
                .subWalUpdatedTime(LocalDateTime.now())
                .build();


    }
}
