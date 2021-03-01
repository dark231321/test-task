package com.home.backend.service.impl;

import com.home.backend.dto.PaymentDto;
import com.home.backend.dto.mapper.PaymentMapper;
import com.home.backend.model.*;
import com.home.backend.repository.CreditOfferRepository;
import com.home.backend.service.ICreditOfferService;
import com.home.backend.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CreditOfferServiceImpl implements ICreditOfferService {

    private final IPaymentService paymentService;
    private final CreditOfferRepository creditOfferRepository;
    private final PaymentMapper paymentMapper;

    public CreditOfferServiceImpl(IPaymentService paymentService,
                                  PaymentMapper paymentMapper,
                                  CreditOfferRepository creditOfferRepository){
        this.paymentMapper = paymentMapper;
        this.creditOfferRepository = creditOfferRepository;
        this.paymentService = paymentService;
    }


    @Override
    public CreditOffer save(CreditOffer creditOffer) {
        paymentService.calculatePayments(creditOffer).forEach(creditOffer::addPayment);
        return creditOfferRepository.save(creditOffer);
    }

    @Override
    public List<PaymentDto> findPaymentsByBank(Bank bank) {
        Optional<CreditOffer> creditOffer = creditOfferRepository.findByBank(bank);
        if(creditOffer.isPresent())
            return creditOffer.get().getPayments().stream().map(paymentMapper::toDto).collect(Collectors.toList());;
        return new ArrayList<>();
    }

    @Override
    public Optional<CreditOffer> findById(long creditOfferId) {
        return creditOfferRepository.findById(creditOfferId);
    }

    @Override
    public boolean delete(long id) {
        try {
            creditOfferRepository.deleteById(id);
        }catch (Exception exc){
            return false;
        }
        return true;
    }

    public List<CreditOffer> findAll(){
        return creditOfferRepository.findAll();
    }
}
