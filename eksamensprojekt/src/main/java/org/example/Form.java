package org.example;

public abstract class Form {
    private String emailEntry;
    private String passwordEntry;
    private Database dataBase;

    protected Form(String emailEntry, String passwordEntry) {
        this.dataBase = new Database();//both forms are going to need access to the same database
        this.emailEntry = emailEntry;
        this.passwordEntry = passwordEntry;
    }

    public String getEmail() {
        return this.emailEntry;
    }

    public String getPassword() {
        return this.passwordEntry;
    }

    public Database getDataBase() {
        return this.dataBase;
    }

    public abstract void executeForm();
}