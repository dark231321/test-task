package com.home.backend.service.impl;

import com.home.backend.exception.NotFoundException;
import com.home.backend.model.Bank;
import com.home.backend.repository.BankRepository;
import com.home.backend.service.IBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankServiceImpl implements IBankService {

    private BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository){
        this.bankRepository = bankRepository;
    }

    @Override
    public Optional<Bank> findById(long bankId){
        Optional<Bank> bankOptional = bankRepository.findById(bankId);
        bankOptional.orElseThrow(NotFoundException::new);
        return bankOptional;
    }

    @Override
    public Optional<Bank> update(Bank bank) {
        Bank oldBank;
        try {
            oldBank = bankRepository.findById(bank.getId()).orElseThrow(NotFoundException::new);
            bankRepository.save(bank);
        }catch (Exception e){
            return Optional.empty();
        }
        return Optional.of(oldBank);
    }

    @Override
    public List<Bank> findAll(){
        return bankRepository.findAll();
    }

    @Override
    public Optional<Bank> save(Bank bank) {
        return Optional.of(bankRepository.save(bank));
    }

    @Override
    public boolean delete(long id){
        bankRepository.findById(id).orElseThrow(NotFoundException::new);
        try {
            bankRepository.deleteById(id);
        } catch (Exception e){
            log.info("EXC", e);
            return false;
        }
        return true;
    }

}
