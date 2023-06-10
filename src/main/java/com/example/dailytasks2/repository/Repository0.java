package com.example.dailytasks2.repository;

import com.example.dailytasks2.domain.Employee;
import com.example.dailytasks2.domain.Entity;
import com.example.dailytasks2.validators.ValidationException;

import java.util.List;
import java.util.Optional;

/**
 * CRUD operations repository interface
 * @param <E> -  type of entities saved in repository
 */

public interface Repository0<ID, E extends Entity<ID>> {

    /**
     * @param entity entity shouldn't be null
     * @return null - if entity is added
     * otherwise it returns the entity (the email adress already exists)
     * @throws ValidationException      if entity is not valid
     * @throws IllegalArgumentException if entity is null
     */
    public Optional<E> create(E entity);

    /**
     * updates an entity
     *
     * @param old_entity shouldn't be null
     * @param new_entity shouldn't be null
     * @return null - if the entity has been updated,
     * otherwise it returns the entity (exemple: email adress doesn't exist)
     * @throws IllegalArgumentException if entity is null
     * @throws ValidationException      if entity is not valid
     */
    public Optional<E> update(E old_entity, E new_entity);

    /**
     * deletes entity by email adress
     *
     * @param id (the id of the entity) should not pe null
     * @return nothing if entity has been deleted
     * @throws IllegalArgumentException if email adress is null
     */
    public Optional<Object> delete(ID id);

    /**
     *
     * @return all entities
     */
    public List<E> read();

    public List<E> findOne(ID id);
}
