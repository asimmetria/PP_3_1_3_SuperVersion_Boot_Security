package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDAO implements ItemDAO<Role> {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> index() {
        TypedQuery<Role> query = entityManager.createQuery("from Role", Role.class);
        return query.getResultList();
    }

    public Role show(Long id) {
        return entityManager.find(Role.class, id);
    }

    public void add(Role role) {
        entityManager.merge(role);
    }

    public void update(Role role) {
        entityManager.merge(role);
    }

    public void delete(Long id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
    }

    public Role findByUsername(String name) {
        try {
            TypedQuery<Role> query = entityManager.createQuery("SELECT u FROM Role u WHERE u.roleName = :roleName", Role.class);
            query.setParameter("roleName", name);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}