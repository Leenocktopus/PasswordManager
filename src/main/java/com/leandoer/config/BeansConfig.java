package com.leandoer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.leandoer"})
public class BeansConfig {

    @Profile("production")
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactoryProduction() {
        String persistenceUnitName = "password_manager";
        log.debug("Initializing entity manager factory for unit: " + persistenceUnitName);
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Profile("test")
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactoryTest() {
        String persistenceUnitName = "pm_test";
        log.debug("Initializing entity manager factory for unit: " + persistenceUnitName);
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }
}
