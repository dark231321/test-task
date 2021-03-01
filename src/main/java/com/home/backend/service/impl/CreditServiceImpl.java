package com.home.backend.service.impl;

import com.home.backend.exception.NotFoundException;
import com.home.backend.model.Credit;
import com.home.backend.repository.CreditRepository;
import com.home.backend.service.ICreditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CreditServiceImpl implements ICreditService {

    private CreditRepository creditRepository;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository){
        this.creditRepository = creditRepository;
    }

    @Override
    public Optional<Credit> findById(long creditId) {
        Optional<Credit> creditOptional = creditRepository.findById(creditId);
        creditOptional.orElseThrow(NotFoundException::new);
        return creditOptional;
    }

    @Override
    public Optional<Credit> update(Credit credit) {
        Credit creditOld;
        try {
            creditOld = creditRepository.findById(credit.getId()).orElseThrow(NotFoundException::new);
            creditRepository.save(credit);
        }catch (Exception e){
            return Optional.empty();
        }
        return Optional.of(creditOld);
    }

    @Override
    public Optional<Credit> save(Credit credit) {

        if(creditRepository.findByCreditLimitAndInterestRate(credit.getCreditLimit(), credit.getInterestRate()) != null){
            return Optional.empty();
        }
        return Optional.of(creditRepository.save(credit));
    }

    @Override
    public List<Credit> findAll() {
        return creditRepository.findAll();
    }

    @Override
    public boolean delete(long id) {
        creditRepository.findById(id).orElseThrow(NotFoundException::new);
        try {
            creditRepository.deleteById(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
