package com.home.backend.service;

import com.home.backend.dto.PaymentDto;
import com.home.backend.model.*;

import java.util.List;
import java.util.Optional;

public interface ICreditOfferService {

    Optional<CreditOffer> findById(long clientId);
    boolean delete(long id);
    List<CreditOffer> findAll();
    CreditOffer save(CreditOffer creditOffer);
    List<PaymentDto> findPaymentsByBank(Bank bank);

}
