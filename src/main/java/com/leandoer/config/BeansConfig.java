package com.leandoer.config;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = {"com.leandoer"})
public class BeansConfig {

    @Profile("production")
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactoryProduction(){
        return Persistence.createEntityManagerFactory("password_manager");
    }

    @Profile("test")
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactoryTest(){
        return Persistence.createEntityManagerFactory("pm_test");
    }
}
