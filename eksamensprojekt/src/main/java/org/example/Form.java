// Denne klasse definerer en abstrakt formularklasse, som skal implementeres af underklasser med specifik funktionalitet.

package org.example;

public abstract class Form {
    // Deklarerer private instansvariabler til brugerens email og password samt en instans af Database-klassen.
    private String emailEntry;
    private String passwordEntry;
    private Database dataBase;
    // Konstruktøren til formularobjektet, som initialiserer databasen og de private instansvariabler til brugerens email og password.
    protected Form(String emailEntry, String passwordEntry) {
        this.dataBase = new Database(); // Begge formularer vil få brug for adgang til den samme database.
        this.emailEntry = emailEntry;
        this.passwordEntry = passwordEntry;
    }

    // Metode til at returnere brugerens email.
    public String getEmail() {
        return this.emailEntry;
    }

    // Metode der returnerer brugerens password.
    public String getPassword() {
        return this.passwordEntry;
    }

    // Metode der returnerer en reference til databasen.
    public Database getDataBase() {
        return this.dataBase;
    }

    // Abstrakt metode, som skal implementeres af underklasser med specifik funktionalitet.
    public abstract void executeForm();
}