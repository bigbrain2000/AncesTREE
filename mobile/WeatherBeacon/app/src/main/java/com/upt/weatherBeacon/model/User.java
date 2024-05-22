package com.upt.weatherBeacon.model;

import java.util.Date;

public class User {
    public String username;
    public String password;

    public String firstName;

    public String lastName;

    public String email;

    public String phoneNumber="";

    public Date birthDate;
    public String address;

    public User(String username, String firstName, String lastName, String password, String email, Date birthDate, String phoneNumber, String address){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address=address;
    }
}
