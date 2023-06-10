package com.example.dailytasks2.service;

import com.example.dailytasks2.domain.Entity;

import java.util.List;

public interface Service0<E extends Entity> {
    public void add(E entity);
    public void delete(E entity);
    public void update(E old_entity, E new_entity);
    public E getEntityById(Integer s);
    public List<E> getAllEntities();

    public boolean testEmail(String email);

}
