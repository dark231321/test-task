package com.home.backend.repository;

import com.home.backend.model.Bank;
import com.home.backend.model.Client;
import com.home.backend.model.Credit;
import com.home.backend.model.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {
    Optional<CreditOffer> findByBank(Bank bank);
}
