package org.example;

import java.util.Scanner;
public class RegLogSystem {
    //Instance variables
    Scanner input = new Scanner(System.in);
    String regOrLogInput;
    String name, email, password;

    //Methods
    public void run() {
        boolean keepGoing = true;
        while(keepGoing==true) {
            System.out.println("Do you wish to (l)ogin or (r)egister");
            regOrLogInput = input.nextLine();

            if(regOrLogInput.contentEquals("l")) {
                System.out.println("Enter email:\n");
                email = input.nextLine();
                System.out.println("Enter password:\n");
                password = input.nextLine();
                LoginForm lForm = new LoginForm(email, password);
                lForm.executeForm();
            }else if(regOrLogInput.contentEquals("r")) {
                System.out.println("Enter name:\n");
                name = input.nextLine();
                System.out.println("Enter email:\n");
                email = input.nextLine();
                System.out.println("Enter password:\n");
                password = input.nextLine();
                RegisterForm rForm = new RegisterForm(name, email, password);
                rForm.executeForm();
            }
            System.out.println("Do you wish to shutdown the system, (y)es or (n)o?");
            String inputText = input.nextLine();
            if(inputText.contentEquals("y")) {
                keepGoing = false;
            }
        }
    }
}