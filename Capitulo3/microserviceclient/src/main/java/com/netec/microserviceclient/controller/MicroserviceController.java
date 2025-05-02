package com.netec.microserviceclient.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netec.microserviceclient.entities.Client;
import com.netec.microserviceclient.exceptions.ClientNotFoundException;
import com.netec.microserviceclient.service.IService;

@RestController
@RequestMapping("/client")
public class MicroserviceController {

    public String username="root";
    public String password="1234";


    @Autowired
    private IService service;
    private static final Logger LOGGER=LoggerFactory.getLogger(MicroserviceController.class);



    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Client cl){
        try{
            if(service.insert(cl)){
                return new ResponseEntity<String>("inserted", HttpStatus.CREATED);
            }
        }catch(Exception ex){
            LOGGER.error("ERROR insert", ex);

        }
        return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<Client>> findAll(){
        try{
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);

        }catch(Exception ex){
            LOGGER.error("ERROR select all", ex);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/id")
    public ResponseEntity<Client> findById(@RequestParam("id") long id){
       try{
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);

       }catch(ClientNotFoundException ex){
        LOGGER.info("client doesn't exists "+id); 
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }catch(Exception ex){
        LOGGER.error("error ", ex);
       }
       return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PutMapping
    public ResponseEntity<String> update(@RequestBody Client cl){
        try{
            if(service.update(cl)){
                return new ResponseEntity<>("updated", HttpStatus.OK);
            }

        }catch(ClientNotFoundException ex){
            LOGGER.info("client doesn't exists "+cl.getId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            LOGGER.error("error update ", ex);

        }
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("id") long id){
        try{
            if(service.deleteById(id)){
                return new ResponseEntity<>("deleted",HttpStatus.OK);
            }

        }catch(ClientNotFoundException ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            LOGGER.info("error delete {}", ex);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }



}
