// Denne klasse er en underklasse af Form-klassen og implementerer en formular til login.

package org.example;

public class LoginForm extends Form {
    // Konstruktør til LoginForm-objektet, som kalder konstruktøren til Form-klassen.
    public LoginForm(String email, String password) {
        super(email, password);
    }
    // Implementerer executeForm-metoden fra Form-klassen og håndterer loginprocessen.
    @Override
    public void executeForm() {
        // Hvis brugeren er registreret i databasen, hent brugeroplysningerne og vis velkomstbeskeden samt start chatten.
        if (getDataBase().isUserRegistered(getEmail(), getPassword())) {
            User newUser = getDataBase().getUser(getEmail(), getPassword());
            WelcomePage welPage = new WelcomePage(newUser);
            welPage.outputMessage();
            welPage.chat();
            //welPage.logoutMessage();
        } else {
            // Hvis brugeren ikke er registreret, udskriv besked om, at brugeren ikke er registreret.
            // Der skal muligvis tilføjes fejlhåndtering på et senere tidspunkt.
            System.out.println("User is not registered");
        }
    }
}