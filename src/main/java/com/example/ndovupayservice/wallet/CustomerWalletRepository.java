package com.example.ndovupayservice.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerWalletRepository extends JpaRepository<CustomerWallet, UUID> {

}
