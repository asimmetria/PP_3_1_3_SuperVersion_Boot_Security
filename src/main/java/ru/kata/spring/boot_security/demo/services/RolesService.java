package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.ItemDAO;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

@Service
public class RolesService implements ItemsService<Role>, GrantedAuthority {
    private final ItemDAO<Role> roleDAO;

    @Autowired
    public RolesService(ItemDAO<Role> roleDAO) {
        this.roleDAO = roleDAO;
    }

    public List<Role> index() {
        return roleDAO.index();
    }

    public Role show(Long id) {
        return roleDAO.show(id);
    }

    public void add(Role role) {
        roleDAO.add(role);
    }

    public void update(Role role) {
        roleDAO.update(role);
    }

    public void delete(Long id) {
        roleDAO.delete(id);
    }


    public Role findByUsername(String name) {
        return roleDAO.findByUsername(name);
    }


    @Override
    public String getAuthority() {
        return null;
    }
}
