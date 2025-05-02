package com.netec.microserviceclient.service;

import java.util.List;

import com.netec.microserviceclient.entities.Client;

public interface IService {
    boolean insert(Client cl);
    List<Client> findAll();
    Client findById(long id);
    boolean deleteById(long id);
    boolean update(Client cl);
}
