package com.leandoer.logic.repository;


import com.leandoer.logic.domain.Password;
import com.leandoer.logic.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class PasswordRepositoryImpl implements PasswordRepository {

    EntityManagerFactory factory;

    @Autowired
    public PasswordRepositoryImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Password password) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(password);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Password> findAllByUser(User user) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager
                .createQuery("select p from Password p where user = ?1", Password.class)
                .setParameter(1, user)
                .getResultList();
    }

    @Override
    public void delete(Password password) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(password) ? password : entityManager.merge(password));
        entityManager.getTransaction().commit();
    }


}
