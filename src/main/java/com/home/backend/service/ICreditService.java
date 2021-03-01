package com.home.backend.service;

import com.home.backend.model.Credit;

import java.util.List;
import java.util.Optional;

public interface ICreditService {
    Optional<Credit> findById(long creditId);
    Optional<Credit> update(Credit credit);
    Optional<Credit> save(Credit credit);
    List<Credit> findAll();
    boolean delete(long id);
}

