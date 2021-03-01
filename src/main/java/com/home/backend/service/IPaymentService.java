package com.home.backend.service;

import com.home.backend.model.Bank;
import com.home.backend.model.CreditOffer;
import com.home.backend.model.Payment;

import java.util.List;
import java.util.Optional;

public interface IPaymentService {
    Optional<Payment> findById(long paymentId);
    Optional<Payment> update(Payment payment);
    List<Payment> findAll();
    Optional<Payment> save(Payment payment);
    boolean delete(long id);
    List<Payment> calculatePayments( CreditOffer creditOffer);
}
