drop schema PUBLIC cascade;

create memory table client
(
    id                  bigint generated by default as identity (start with 1) primary key,
    last_name           VARCHAR(20) NOT NULL,
    first_name          VARCHAR(20) NOT NULL,
    middle_name         VARCHAR(20) NOT NULL,
    phone_number        VARCHAR(11),
    email               VARCHAR(50),
    passport            VARCHAR(10) unique
);

create memory table credit
(
    id                  bigint generated by default as identity (start with 1) primary key,
    credit_limit        double,
    interest_rate       double
);

create memory table bank
(
    id                  bigint generated by default as identity (start with 1) primary key,
    fk_client           bigint foreign key references client(id),
    fk_credit           bigint foreign key references credit(id)
);

create memory table credit_offer
(
    id                  bigint generated by default as identity (start with 1) primary key,
    credit_sum          bigint,
    credit_period       bigint,
    fk_credit           bigint foreign key  references credit(id),
    fk_client           bigint foreign key  references client(id),
    fk_bank             bigint foreign key  references bank(id) ON DELETE CASCADE
);

create memory table payment
(
    id                  bigint generated by default as identity (start with 1) primary key,
    payment_amount      double,
    payment_date        date,
    payment_body        double,
    interest_repayment  double,
    fk_credit_offer     bigint foreign key references credit_offer (id)
);

COMMIT;