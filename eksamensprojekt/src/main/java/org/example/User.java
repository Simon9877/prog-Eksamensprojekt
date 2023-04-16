// Denne klasse definerer en bruger med navn, email og password, og indeholder også en id til brug i databasen.

package org.example;

public class User {
    // Private instansvariabler til id, navn, email og password.
    private int id;
    private String name;
    private String email;
    private String password;

    // Tom konstruktør
    public User() {
    }

    // Konstruktør, som initialiserer instansvariablerne name, email og password.
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Metode til at returnere brugerens id.
    public int getId() {
        return id;
    }

    // Metode til at sætte brugerens id.
    public void setId(int id) {
        this.id = id;
    }

    // Metode til at returnere brugerens email.
    public String getEmail() {
        return this.email;
    }

    // Metode til at returnere brugerens password.
    public String getPassword() {
        return this.password;
    }

    // Metode til at returnere brugerens navn.
    public String getName() {
        return this.name;
    }
}