package com.example.dailytasks2.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
