package com.home.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "client")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends AbstractEntity {

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "passport")
    private String passport;


    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName  + ", " + phoneNumber;
    }
}
