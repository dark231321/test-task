package com.home.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "payment")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends AbstractEntity {

    @Column(name = "payment_amount")
    private double paymentAmount;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_body")
    private double paymentBody;

    @Column(name = "interest_repayment")
    private double interestRepayment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_credit_Offer")
    private CreditOffer creditOffer;

}
