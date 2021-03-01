package com.home.backend.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Setter
@Getter
@Table(name = "bank")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Bank extends AbstractEntity {


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_credit")
    private Credit credits;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_client")
    private Client clients;
}
