package com.home.backend.model;


import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "credit_offer")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreditOffer extends AbstractEntity {

    @Column(name = "credit_sum")
    private double creditSum;

    @Column(name = "credit_period")
    private int creditPeriod;

    @ManyToOne
    @JoinColumn(name = "fk_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "fk_credit")
    private Credit credit;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_bank")
    private Bank bank;

    @OneToMany(mappedBy = "creditOffer",fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setCreditOffer(this);
    }

    public void removeStudent(Payment payment) {
        payments.remove(payment);
        payment.setCreditOffer(null);
    }

}
