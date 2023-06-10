package com.example.dailytasks2.domain;

import static com.example.dailytasks2.utils.StringUtils.matchesAny;

public class Manager extends Entity<Integer>{

    private String firstName;
    private String lastName;
    private String emailAdress;


    public Manager(Integer s, String firstName, String lastName, String emailAdress) {
        super(s);
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAdress = emailAdress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAdress='" + emailAdress + '\'' +
                '}';
    }

    public boolean matches(String input) {
        return matchesAny(input, getId().toString(), getEmailAdress(),
                getFirstName(), getLastName());
    }
}
