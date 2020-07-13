package com.leandoer.logic.repository;


import com.leandoer.logic.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Repository
public class UserRepositoryImpl implements UserRepository{

    EntityManagerFactory factory;

    @Autowired
    public UserRepositoryImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }


    @Override
    public void save(User user) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }



    @Override
    public User findUserByUsername(String username) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager
                .createQuery("select u from User u where u.username = ?1", User.class)
                .setParameter(1, username)
                .getResultList().get(0);
    }

    @Override
    public void delete(User user) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
        entityManager.getTransaction().commit();
    }
}
