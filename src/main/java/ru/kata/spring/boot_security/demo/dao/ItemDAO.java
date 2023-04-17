package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface ItemDAO<T> {

    List<T> index();

    T show(Long id);

    void add(T t);

    void update(T t);

    void delete(Long id);

    T findByUsername(String name);

}
