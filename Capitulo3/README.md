# Práctica 2. Análisis de seguridad y códificación de un API Rest Spring Boot

En este laboratorio se espera que desarrolles una API REST utilizando Spring Boot, aplicando buenas prácticas de seguridad en el proceso. Además, deberás realizar análisis de código con herramientas como Snyk y SonarQube, con el objetivo de identificar posibles vulnerabilidades.

## Objetivos:

Al finalizar la práctica, serás capaz de:
- Desarrollar un API RESTful aplicando las buenas prácticas en el diseño de código con Spring Boot. 
- Analizar nuestro código usando:
	- Snyk para buscar vulnerabilidades.
 	- SonarQube para la búsqueda de vulnerabilidades en nuestro código.

## Duración aproximada:
- 60 minutos.

---
<!--Este fragmento es la barra de 
navegación-->

<div style="width: 400px;">
        <table width="50%">
            <tr>
                <td style="text-align: center;">
                    <a href="../Capitulo2/"><img src="../images/anterior.png" width="40px"></a>
                    <br>anterior
                </td>
                <td style="text-align: center;">
                   <a href="../README.md">Lista Laboratorios</a>
                </td>
<td style="text-align: center;">
                    <a href="../Capitulo4/"><img src="../images/siguiente.png" width="40px"></a>
                    <br>siguiente
                </td>
            </tr>
        </table>
</div>

---

## Objetivo visual:

![diagrama](../images/2/diagrama.png)

## Instrucciones
> **IMPORTANTE:** Antes de iniciar la práctica es necesario tener una cuenta de **gmail** y una cuenta **GitHub** para el registro de las herramientas. 

Esta práctica se encuentra separada en las siguientes secciones:

- **[Códificar Microservicio Client](#códifica-microservicio-client-return)**
- **[Análisis de seguridad de código con SNYK](#análisis-de-seguridad-de-código-snyk-return)**
- **[Análisis de seguridad de código con SONARQUBE](#análisis-de-seguridad-de-código-con-sonarqube-return)**

### Códifica microservicio client [return](#instrucciones)

1. Abre Visual Studio Code.
2. Pulsa la combinación de teclas **ctrl+shift+p** para abrir la paleta de comandos.
3. Busca la opción **Spring initializr: Create a Maven Project**.
    - **Spring Boot Version:** 3.4.5
    - **Specify project language:** java
    - **input group id:** com.netec
    - **Artifact id:** microserviceclient
    - **Packaging type:** Jar
    - **Java version:** 17
    - **Dependencies:**
        - **Spring Web**
        - **Spring Boot DevTools**
        - **Spring Data JPA**
        - **MySQL Driver**
    - **Abrir proyecto**

4. Crea los siguientes paquetes para organizar nuestra arquitectura. 

![alt text](../images/2/1.png)

5. En el paquete **entities** añade el siguiente código. 

```java
package com.netec.microserviceclient.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="data")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(length = 50)
    private String name;

    @Column(length=100)
    private String address;

    @Column(length = 150)
    private String email;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + "]";
    }
}
```

6. Añade en el paquete **dao** el siguiente código: 

```java
package com.netec.microserviceclient.dao;

import org.springframework.data.repository.CrudRepository;

import com.netec.microserviceclient.entities.Client;

public interface IMicroserviceDAO  extends CrudRepository<Client, Long>{

}
```

7. Añade en el paquete **exceptions** el siguiente código:

```java
package com.netec.microserviceclient.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message){
        super(message);
    }

}
```

8. Añade en el paquete **services** el siguiente código: 

```java
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
```

```java
package com.netec.microserviceclient.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netec.microserviceclient.dao.IMicroserviceDAO;
import com.netec.microserviceclient.entities.Client;
import com.netec.microserviceclient.exception.ClientNotFoundException;

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
```

9. Añade en el paquete **controller** el siguiente código:

```java
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
import com.netec.microserviceclient.exception.ClientNotFoundException;
import com.netec.microserviceclient.services.IService;

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
```

10. Añade en el archivo **application.properties** la siguiente configuración: 

```properties
spring.application.name=micro-client
server.port=8082

#hibernate config
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update

#datasource
spring.datasource.url=jdbc:mysql://localhost:3306/datab
spring.datasource.username=root
spring.datasource.password=1234
```
11. Inicia el microservicio usando el menú de Spring boot de Visual Studio Code.

![alt text](../images/2/2.png)

12. Prueba las siguientes operaciones en **POSTMAN o INSOMNIA**: 
    - **GET ALL**:
        ```bash
        curl --request GET --url http://localhost:8082/client
        ```
    - **GET By ID**
        ```bash
        curl --request GET --url 'http://localhost:8082/client/id?id=2'
        ```
    - **INSERT**
        ```bash
        curl --request POST --url http://localhost:8082/client --header 'Content-Type: application/json' --data '{ "name":"edgardo","address":"su casa","email":"egar29047@gmail.com"
        }'
        ```
    - **UPDATE**
        ```bash
        curl --request PUT --url http://localhost:8082/client --header 'Content-Type: application/json' --data '{
		"id": 2,
		"name": "edgardo",
		"address": "su otra casa",
		"email": "zaira@gmail.com"
	    }'
        ```
    - **DELETE**
        ```bash
        curl --request DELETE --url 'http://localhost:8082/client?id=4'
        ```


### Análisis de seguridad de código SNYK [return](#instrucciones)

1. Abre el siguiente enlace **https://app.snyk.io/login**, e inicia sesión usando su cuenta de **google.**

![alt text](../images/2/3.png)

2. Instala en **Visual Studio Code** la extensión  **Snyk Security.**

![alt text](../images/2/4.png)

3. En las opciones de **Snyk** selecciona la opción **Enable Synk Code and start analyzing.**

![alt text](../images/2/5.png)

> **IMPORTANTE:** Al sincronizar nuestro proyecto a **Snyk** nos pedira iniciar sesión con nuestra cuenta creada en los puntos anteriores. 

4. En la plataforma de **Snyk** activa el análisis de código. 

![alt text](../images/2/6.png)

5. Reescanea el proyecto completo y espera el análisis de seguridad.

![alt text](../images/2/7.png)


6. Analiza las recomendaciones de seguridad que nos entrega la herramienta.


### Análisis de seguridad de código con SONARQUBE [return](#instrucciones)

1. Abre el siguiente enlace **https://sonarcloud.io/login** e inicia sesión usando tu cuenta de **GitHub.**

![alt text](../images/2/8.png)

2. Al iniciar tu cuenta de **Sonar Cloud**, añade un nuevo proyecto. 

![alt text](../images/2/9.png)


3. Selecciona la opción **create a project manually.**

![alt text](../images/2/10.png)


4. Crea una nueva organización con el nombre **netec.**

5. Añade un proyecto **Analyze projects** con las siguientes opciones:

![alt text](../images/2/11.png)

6. Configura el set up project con las siguientes opciones:

![alt text](../images/2/12.png)

7. Ahora en **Visual Studio Code**, instala la extensión **SonarQube for IDE**:

![alt text](../images/2/13.png)

8. En las opciones de la extensión configura **Add SonarQube Cloud Connection.**

9. Pulsa **Generate token**. En esta opción nos pedira iniciar sesión con nuestra cuenta de **SonarQube** y  seleccionar la **Organización.**

![alt text](../images/2/14.png)

10. Enlaza el proyecto creado en **Sonar Cloud** con el proyecto que tenemos con **Visual Studio Code.** 

![alt text](../images/2/15.png)

11. Espera a que termine de analizar todos los archivos de **Microservice Client.** 

12. En **Security HOSTSPOSTS** observaremos el reporte de posibles problemas de seguridad:

![alt text](../images/2/16.png)

13. Si visitas cada archivo de tu proyecto observaras que **Sonarqube** realiza recomendaciones de código limpio y te permite mejorarlo. 

![alt text](../images/2/17.png)

14. Analiza los resultados.

## Resultado esperado
Al final de la práctica el alumno podrá observar los resultados de los análisis de 2 herramientas orientadas a la seguridad de código y mejora continua del mismo. 

![alt text](../images/2/18.png)

