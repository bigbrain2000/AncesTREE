package com.upt.weatherBeacon.model;

import java.util.Date;

public class User {
    public int id;
    public String username;
    public String password;

    public String firstName;

    public String lastName;

    public String email;

    public String phoneNumber="";

    public Date birthDate;

    public User(String username, String firstName, String lastName, String password, String email, Date birthDate){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.birthDate = birthDate;
    }
}
