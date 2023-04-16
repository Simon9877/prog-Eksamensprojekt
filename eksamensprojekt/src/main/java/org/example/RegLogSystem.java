// Denne klasse definerer selve registrerings- og loginsystemet.

package org.example;

import java.util.Scanner;

public class RegLogSystem {
    // Instansvariabler
    Scanner input = new Scanner(System.in);
    String regOrLogInput;
    String name, email, password;
    // Metode til at køre registrerings- og loginsystemet.
    public void run() {
        boolean keepGoing = true;
        while (keepGoing) {
            // Viser prompt til login eller registrering.
            System.out.println("Do you wish to (l)ogin or (r)egister");
            regOrLogInput = input.nextLine();

            // Hvis brugeren vælger login, promptes der for email og password, og loginformularen udføres.
            if (regOrLogInput.contentEquals("l")) {
                System.out.println("Enter email:\n");
                email = input.nextLine();
                System.out.println("Enter password:\n");
                password = input.nextLine();
                LoginForm lForm = new LoginForm(email, password);
                lForm.executeForm();
                // Hvis brugeren vælger registrering, promptes der for navn, email og password, og registreringsformularen udføres.
            } else if (regOrLogInput.contentEquals("r")) {
                System.out.println("Enter name:\n");
                name = input.nextLine();
                System.out.println("Enter email:\n");
                email = input.nextLine();
                System.out.println("Enter password:\n");
                password = input.nextLine();
                RegisterForm rForm = new RegisterForm(name, email, password);
                rForm.executeForm();
            }

            // Spørg om brugeren vil afslutte systemet. Hvis ja, sæt keepGoing til false for at afslutte while-loopet.
            System.out.println("Do you wish to shutdown the system, (y)es or (n)o?");
            String inputText = input.nextLine();
            if (inputText.contentEquals("y")) {
                keepGoing = false;
            }
        }
    }
}