package com.example.dailytasks2.domain;

import static com.example.dailytasks2.utils.StringUtils.matchesAny;

public class Employee extends Entity<Integer>{

    private String firstName;
    private String lastName;
    private String emailAdress;

    private EmployeeStatus status;

    public Employee(Integer integer, String firstName, String lastName, String emailAdress, EmployeeStatus status) {
        super(integer);
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAdress = emailAdress;
        this.status = status;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public boolean matches(String input) {
        return matchesAny(input, getId().toString(), getEmailAdress(),
                getFirstName(), getLastName(), getStatus().toString());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAdress='" + emailAdress + '\'' +
                ", status=" + status +
                '}';
    }
}
