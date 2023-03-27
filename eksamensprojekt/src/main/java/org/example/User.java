package org.example;

public class User {
    private String name;
    private String dateOfBirth;
    private String email;
    private String password;


    public User(String name, String dateOfBirth, String email, String password) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
    }



    public User() {

    }



    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }
}