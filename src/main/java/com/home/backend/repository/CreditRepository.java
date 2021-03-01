package com.home.backend.repository;

import com.home.backend.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    Credit findByCreditLimitAndInterestRate(double creditLimit, double interestRate);
}
