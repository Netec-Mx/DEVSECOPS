package com.netec.microserviceclient.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netec.microserviceclient.dao.IMicroserviceDAO;
import com.netec.microserviceclient.entities.Client;
import com.netec.microserviceclient.exceptions.ClientNotFoundException;

@Service
public class ServiceImpl implements IService{

    public String jdbcURL="jdbc:mysql://localhost:3306/db";

    @Autowired
    private IMicroserviceDAO dao;

    @Override
    public boolean insert(Client cl) {
        if(cl.getId()==0){
            return dao.save(cl)!=null;
        }

        return false;
    }

    @Override
    public List<Client> findAll() {
        return (List<Client>) dao.findAll();
            
    }

    @Override
    public Client findById(long id) {
       return dao.findById(id)
       .orElseThrow(()->new  ClientNotFoundException("client desn't exists "+id));
    }

    @Override
    public boolean deleteById(long id) {
        if(dao.existsById(id)){
            dao.deleteById(id);
            return true;
        }
        throw new ClientNotFoundException("client doesn't exists "+id);
    }

    @Override
    public boolean update(Client cl) {
        if(dao.existsById(cl.getId())){
           return dao.save(cl)!=null;
        }
        throw new ClientNotFoundException("client doesn't exists "+cl.getId());
    }
}
