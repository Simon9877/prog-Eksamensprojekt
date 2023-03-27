package org.example;

import java.util.Scanner;

public class WelcomePage {
    private User loggedInUser;
    private Scanner input = new Scanner(System.in);

    public WelcomePage(User loggingInUser) {
        this.loggedInUser = loggingInUser;
    }

    public void outputMessage() {
        System.out.println("Hi "+loggedInUser.getName());
    }

    public void logoutMessage() {
        boolean keepGoing = true;
        while(keepGoing==true) {
            System.out.println("Do you wish to logout, (y)es or (n)o");
            String inputText = input.nextLine();
            if(inputText.contentEquals("y")) {
                keepGoing=false;
            }
        }

    }
}