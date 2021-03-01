package com.home.backend.service.impl;

import com.home.backend.exception.NotFoundException;
import com.home.backend.model.Client;
import com.home.backend.repository.ClientRepository;
import com.home.backend.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClientServiceImpl implements IClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> findById(long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        optionalClient.orElseThrow(NotFoundException::new);
        return optionalClient;
    }

    @Override
    public Optional<Client> update(Client client) {
        Client oldClient;
        try {
            oldClient = clientRepository.findById(client.getId()).orElseThrow(NotFoundException::new);
            clientRepository.save(client);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(oldClient);
    }

    @Override
    public Optional<Client> save(Client client) {
        Optional<Client> optionalClient = clientRepository.findByPassport(client.getPassport());
        if (optionalClient.isPresent())
            return Optional.empty();
        return Optional.of(clientRepository.save(client));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public boolean delete(long id) {
        clientRepository.findById(id).orElseThrow(NotFoundException::new);
        try {
            clientRepository.deleteById(id);
        } catch (Exception e){
            log.error("Got exception while delete client", e);
            return false;
        }
        return true;
    }
}
