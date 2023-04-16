// Denne klasse er en underklasse af Form-klassen og implementerer en formular til registrering af en ny bruger.

package org.example;

public class RegisterForm extends Form {
    // Deklarerer en privat instansvariabel til brugerens navn.
    private String name;
    // Konstruktør til RegisterForm-objektet, som kalder konstruktøren til Form-klassen og initialiserer navnevariablen.
    public RegisterForm(String name, String email, String password) {
        super(email, password);
        this.name = name;
    }

    // Metode til at returnere brugerens navn.
    public String getName() {
        return this.name;
    }

    // Implementerer executeForm-metoden fra Form-klassen og håndterer registreringsprocessen.
    @Override
    public void executeForm() {
        // Opretter en ny bruger med de angivne oplysninger og tilføjer den til databasen.
        User newUser = new User(getName(), getEmail(), getPassword());
        getDataBase().addUserToDatabase(newUser);
        // Viser velkomstbesked og starter chatten.
        WelcomePage welPage = new WelcomePage(newUser);
        welPage.outputMessage();
        welPage.chat();
        //welPage.logoutMessage();
    }
}