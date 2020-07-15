package com.leandoer;

import com.leandoer.config.BeansConfig;
import com.leandoer.logic.domain.User;
import com.leandoer.logic.repository.UserRepository;
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
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManagerFactory factory;

    static boolean isSetUp = false;

    @Before
    public void setUp(){
        if (!isSetUp){
            EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createQuery("delete from User").executeUpdate();
            entityManager.getTransaction().commit();
            isSetUp = true;
        }
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setUsername("leandoer");
        user.setPassword("1111");
        userRepository.save(user);
        EntityManager entityManager = factory.createEntityManager();
        List<User> selectedUsers = entityManager
                .createQuery("select u from User u", User.class)
                .getResultList();
        assertEquals(1, selectedUsers.size());
        assertEquals(user, selectedUsers.get(0));
    }

    @Test
    public void findUserByUsername() {
        User user = new User();
        user.setUsername("leandoer");
        user.setPassword("1111");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        User selectedUser = userRepository.findUserByUsername("leandoer");
        assertEquals(user, selectedUser);
    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setUsername("leandoer");
        user.setPassword("1111");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        List<User> selectedUsers = entityManager
                .createQuery("select u from User u", User.class)
                .getResultList();

        userRepository.delete(selectedUsers.get(0));
        selectedUsers = entityManager
                .createQuery("select u from User u", User.class)
                .getResultList();
        assertEquals(0, selectedUsers.size());
    }

    @After
    public void cleanUp(){
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from User").executeUpdate();
        entityManager.getTransaction().commit();

    }
}
