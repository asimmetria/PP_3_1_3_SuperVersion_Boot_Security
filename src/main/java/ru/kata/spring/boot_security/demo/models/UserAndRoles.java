package ru.kata.spring.boot_security.demo.models;

import java.util.List;

public class UserAndRoles {
    private User user;
    private List<Long> selectedRoles;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Long> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<Long> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }
}
