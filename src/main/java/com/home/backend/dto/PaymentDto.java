package com.home.backend.dto;

import com.home.backend.model.Payment;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class PaymentDto {
    private long id;
    private double paymentAmount;
    private Date paymentDate;
    private double paymentBody;
    private double interestRepayment;

}
