package com.netec.microserviceclient.dao;

import org.springframework.data.repository.CrudRepository;

import com.netec.microserviceclient.entities.Client;

public interface IMicroserviceDAO  extends CrudRepository<Client, Long>{

}
