package com.home.backend.service;

import com.home.backend.model.Bank;

import java.util.List;
import java.util.Optional;

public interface IBankService {
    Optional<Bank> findById(long bankId);
    Optional<Bank> update(Bank bank);
    List<Bank> findAll();
    Optional<Bank> save(Bank bank);
    boolean delete(long id);
}
