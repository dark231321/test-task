package com.home.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.text.NumberFormat;
import java.util.List;


@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit")
public class Credit extends AbstractEntity {

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Column(name = "interest_rate")
    private Double interestRate;

    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER)
    private List<CreditOffer> creditOffers;

    public String getFormattedCreditLimit() {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return f.format(creditLimit);
    }

    public String getFormattedInterestRate() {
        return interestRate + "%";
    }


    @Override
    public String toString() {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return  f.format(creditLimit) + ", " +  interestRate + '%';
    }
}
