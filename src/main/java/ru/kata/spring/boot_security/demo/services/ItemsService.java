package ru.kata.spring.boot_security.demo.services;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemsService<T> {
    List<T> index();

    T show(Long id);

    @Transactional
    void add(T t);

    @Transactional
    void update(T t);

    @Transactional
    void delete(Long id);

    T findByUsername(String name);

}
