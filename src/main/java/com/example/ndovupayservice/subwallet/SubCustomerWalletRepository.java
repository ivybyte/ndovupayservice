package com.example.ndovupayservice.subwallet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubCustomerWalletRepository  extends JpaRepository<SubCustomerWallet, UUID>  {

}
