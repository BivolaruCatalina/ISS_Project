package com.example.dailytasks2.validators;

import com.example.dailytasks2.domain.User;

public class UserValidator implements Validator<User>{
    @Override
    public void validate(User entity) throws ValidationException {
        validateEmailAdress(entity.getEmailAdress());
        validatePassword(entity.getPassword());
    }

    private void validateEmailAdress(String emailAdress) throws ValidationException{
        if (emailAdress == null)
            throw new ValidationException("Email adress should contain at least 1 character!");
        if (emailAdress.isEmpty())
            throw new ValidationException("Email adress should contain at least 1 character!");
    }
    private void validatePassword(String password) throws ValidationException{
        if (password == null)
            throw new ValidationException("Password should contain at least 6 characters!");
        if (password.isEmpty())
            throw new ValidationException("Password should contain at least 6 characters!");
        if(password.length()<6)
            throw new ValidationException("Password should contain at least 6 characters!");
    }
}
