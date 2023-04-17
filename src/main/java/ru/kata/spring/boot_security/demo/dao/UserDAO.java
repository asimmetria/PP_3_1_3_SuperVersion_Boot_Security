package ru.kata.spring.boot_security.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Repository
public class UserDAO implements ItemDAO<User>  {
    @PersistenceContext
    private EntityManager entityManager;

    public List<User> index() {
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    public User show(Long id) {
        return entityManager.find(User.class, id);
    }

    public void add(User user) {

        entityManager.merge(user);
    }

    public void update(User user) {
        User existingUser = entityManager.find(User.class, user.getUser_id());
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setAge(user.getAge());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setRoles(user.getRoles());
            entityManager.merge(existingUser);
        }
    }


    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.createNativeQuery("DELETE FROM user_roles ur WHERE user_id = :userId")
                    .setParameter("userId", user.getUser_id())
                    .executeUpdate();
            entityManager.remove(user);
        }
    }


    public User findByUsername(String username) {
        try {
            TypedQuery<User> query = entityManager.createQuery("Select u from User u left join fetch u.roles where u.username=:username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}