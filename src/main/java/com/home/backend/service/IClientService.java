package com.home.backend.service;

import com.home.backend.model.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    Optional<Client> findById(long clientId);
    Optional<Client> update(Client client);
    Optional<Client> save(Client client);
    List<Client> findAll();
    boolean delete(long id);
}
