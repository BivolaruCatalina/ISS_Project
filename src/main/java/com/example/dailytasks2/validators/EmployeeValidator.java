package com.example.dailytasks2.validators;

import com.example.dailytasks2.domain.Employee;
import com.example.dailytasks2.domain.Manager;

public class EmployeeValidator implements Validator<Employee> {

    @Override
    public void validate(Employee entity) throws ValidationException {
        validateFirstName(entity.getFirstName());
        validateLastName(entity.getLastName());
        validateEmailAdress(entity.getEmailAdress());
    }

    private void  validateFirstName(String firstName) throws ValidationException{
        if (firstName == null)
            throw new ValidationException("First name mustn't be null!");
        if (firstName.isEmpty())
            throw new ValidationException("First name mustn't be null!");
    }

    private void  validateLastName(String lastName) throws ValidationException{
        if (lastName == null)
            throw new ValidationException("Last name mustn't be null!");
        if (lastName.isEmpty())
            throw new ValidationException("Last name mustn't be null!");
    }
    private void validateEmailAdress(String emailAdress) throws ValidationException{
        if (emailAdress == null)
            throw new ValidationException("Email adress should contain at least 1 character!");
        if (emailAdress.isEmpty())
            throw new ValidationException("Email adress should contain at least 1 character!");
    }
}
