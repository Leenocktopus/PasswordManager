package com.leandoer;


import com.leandoer.config.BeansConfig;
import com.leandoer.logic.domain.Password;
import com.leandoer.logic.domain.User;
import com.leandoer.logic.repository.PasswordRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.Assert.assertEquals;
@Slf4j
@Component
@ActiveProfiles("test")
@ContextConfiguration(classes = BeansConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PasswordRepositoryTest {

    @Autowired
    EntityManagerFactory factory;

    @Autowired
    PasswordRepository passwordRepository;


    static boolean isSetUp = false;

    @Before
    public void setUp(){
        if (!isSetUp){
            User user = new User();
            user.setUsername("leandoer");
            user.setPassword("1111");
            EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createQuery("delete from User").executeUpdate();
            entityManager.createQuery("delete from Password").executeUpdate();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            isSetUp = true;
        }
    }

    @Test
    public void addPassword() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.createQuery("select u from User u", User.class).getSingleResult();
        entityManager.getTransaction().commit();
        Password password = new Password();
        password.setUsername("foo");
        password.setPassword("bar");
        password.setResourceUrl("spring.io");
        password.setDescription("baz");
        password.setUser(user);
        passwordRepository.save(password);
        List<Password> selectedPasswords = entityManager
                .createQuery("select p from Password p", Password.class)
                .getResultList();
        assertEquals(1, selectedPasswords.size());
        assertEquals(password, selectedPasswords.get(0));
    }

    @Test
    public void getAllPasswords() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.createQuery("select u from User u", User.class).getSingleResult();
        Password first = new Password();
        Password second = new Password();
        first.setUser(user);
        second.setUser(user);
        first.setUsername("foo");
        second.setUsername("bar");
        entityManager.persist(first);
        entityManager.persist(second);
        entityManager.getTransaction().commit();

        List<Password> passwords = passwordRepository.findAllByUser(user);
        assertEquals(2, passwords.size());
        passwords.forEach(password -> assertEquals(user, password.getUser()));
    }


    @Test
    public void deletePassword() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.createQuery("select u from User u", User.class).getSingleResult();
        Password password = new Password();
        password.setUser(user);
        password.setUsername("a Username");
        entityManager.persist(password);
        entityManager.getTransaction().commit();
        passwordRepository.delete(password);
        List<Password> selectedPasswords = entityManager
                .createQuery("select p from Password p", Password.class)
                .getResultList();
        assertEquals(0, selectedPasswords.size());
    }
    @Test
    public void deleteAllPasswords() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.createQuery("select u from User u", User.class).getSingleResult();
        Password first = new Password();
        Password second = new Password();
        first.setUser(user);
        first.setUsername("first");
        second.setUser(user);
        second.setUsername("second");
        entityManager.persist(first);
        entityManager.persist(second);
        entityManager.getTransaction().commit();
        passwordRepository.deleteAllByUser(user);
        List<Password> selectedPasswords = entityManager
                .createQuery("select p from Password p", Password.class)
                .getResultList();
        assertEquals(0, selectedPasswords.size());
    }
    @After
    public void cleanUp(){
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Password").executeUpdate();
        entityManager.getTransaction().commit();
    }
}
