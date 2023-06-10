package com.example.dailytasks2.domain;

import java.util.List;

import static com.example.dailytasks2.utils.StringUtils.matchesAny;

public class User extends Entity<Integer>{

    private String emailAdress;
    private String password;

    public User(Integer s, String emailAdress, String password) {
        super(s);
        this.emailAdress = emailAdress;
        this.password = password;
    }


    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailAdress='" + emailAdress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean matches(String input) {
        return matchesAny(input, getId().toString(), getEmailAdress(), getPassword());
    }
}
