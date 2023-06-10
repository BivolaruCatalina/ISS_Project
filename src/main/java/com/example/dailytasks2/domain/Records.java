package com.example.dailytasks2.domain;

import com.example.dailytasks2.utils.Constants;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.example.dailytasks2.utils.StringUtils.matchesAny;

public class Records extends Entity<Integer>{

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private LocalTime logIn;

    private String emailAdress;

    private String firstName;

    private String lastName;

    public Records(Integer integer, LocalTime logIn, String emailAdress, String firstName, String lastName) {
        super(integer);
        this.logIn = logIn;
        this.emailAdress = emailAdress;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public LocalTime getLogIn() {
        return logIn;
    }

    public  void setLogIn(LocalTime logIn) {
        this.logIn = logIn;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
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

    @Override
    public String toString() {
        return "Records{" +
                "logIn=" + logIn +
                ", emailAdress='" + emailAdress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public boolean matches(String input) {
        return matchesAny(input, getId().toString(), getEmailAdress(),
                getFirstName(), getLastName(), getLogIn().toString());
    }
}
