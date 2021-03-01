package com.home.backend.service.impl;

import com.home.backend.exception.NotFoundException;
import com.home.backend.model.Bank;
import com.home.backend.model.Client;
import com.home.backend.model.CreditOffer;
import com.home.backend.model.Payment;
import com.home.backend.repository.CreditOfferRepository;
import com.home.backend.repository.PaymentRepository;
import com.home.backend.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentService implements IPaymentService {

    private PaymentRepository paymentRepository;

    @Override
    public Optional<Payment> findById(long paymentId) {
        return Optional.of(paymentRepository.getOne(paymentId));
    }

    @Override
    public Optional<Payment> update(Payment payment) {
        Payment oldPayment;
        try {
            oldPayment = paymentRepository.findById(payment.getId()).orElseThrow(NotFoundException::new);
            paymentRepository.save(payment);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(oldPayment);
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public Optional<Payment> save(Payment payment) {
        return Optional.of(paymentRepository.save(payment));
    }

    @Override
    public boolean delete(long id) {
        paymentRepository.findById(id).orElseThrow(NotFoundException::new);
        try {
            paymentRepository.deleteById(id);
        } catch (Exception e){
            log.error("Got exception while delete client", e);
            return false;
        }
        return true;
    }

    @Override
    public List<Payment> calculatePayments(CreditOffer creditOffer) {
        double scale = Math.pow(10, 2);
        double interestRate = creditOffer.getCredit().getInterestRate();
        int months = creditOffer.getCreditPeriod() * 12;
        double sum = creditOffer.getCreditSum();
        List<Payment> payments = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        interestRate /= 1200;
        double paymentPerMonth = Math.ceil(sum * (interestRate * Math.pow((1 + interestRate), months) / (Math.pow(1 + interestRate, months) - 1)) * scale) /scale;
        for (int i = 1; i <= months; i++) {
            double interestRepayment = Math.ceil(sum * interestRate * scale) / scale;
            double paymentBody = Math.ceil((paymentPerMonth - interestRepayment) * scale) / scale;
            sum = Math.ceil((sum - paymentBody) * scale) / scale;
            payments.add(Payment.builder()
                    .interestRepayment(interestRepayment)
                    .paymentAmount(paymentPerMonth)
                    .paymentBody(paymentBody)
                    .paymentDate(Date.valueOf(localDate.plusMonths(i)))
                    .creditOffer(creditOffer)
                    .build());
        }
        return payments;
    }

}
