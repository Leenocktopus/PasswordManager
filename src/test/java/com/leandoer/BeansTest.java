package com.leandoer;

import com.leandoer.config.BeansConfig;
import com.leandoer.logic.repository.PasswordRepository;
import com.leandoer.logic.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManagerFactory;
;import static org.junit.Assert.*;

@Slf4j
@Component
@ActiveProfiles("test")
@ContextConfiguration(classes = BeansConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BeansTest {

    @Autowired
    EntityManagerFactory factory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordRepository passwordRepository;

    @Test
    public void injectedBeansAreNotNull(){
        assertNotNull(factory);
        assertNotNull(userRepository);
        assertNotNull(passwordRepository);
    }
    @Test
    public void shouldBeTestConfiguration(){

        log.info("Checking persistence unit name for test configuration");
        String name = factory.getProperties().get("hibernate.ejb.persistenceUnitName").toString();
        log.info("Persistence unit name is: {}", name);
        assertEquals("pm_test", name);
    }



}
