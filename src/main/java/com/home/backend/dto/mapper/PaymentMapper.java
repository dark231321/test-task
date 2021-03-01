package com.home.backend.dto.mapper;

import com.home.backend.dto.PaymentDto;
import com.home.backend.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toModel(PaymentDto paymentDto){
        return Payment.builder().id(paymentDto.getId())
                .paymentAmount(paymentDto.getPaymentAmount())
                .paymentBody(paymentDto.getPaymentBody())
                .paymentDate(paymentDto.getPaymentDate())
                .build();
    }

    public PaymentDto toDto(Payment payment){
        return payment == null ? null :
                PaymentDto.builder()
                        .id(payment.getId())
                        .interestRepayment(payment.getInterestRepayment())
                        .paymentAmount(payment.getPaymentAmount())
                        .paymentBody(payment.getPaymentBody())
                        .paymentDate(payment.getPaymentDate())
                        .build();
    }
}
