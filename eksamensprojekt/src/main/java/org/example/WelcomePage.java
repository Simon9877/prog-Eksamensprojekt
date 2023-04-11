package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WelcomePage {
    private User loggedInUser;
    private Scanner input = new Scanner(System.in);

    public WelcomePage(User loggingInUser) {
        this.loggedInUser = loggingInUser;
    }

    public void outputMessage() {
        System.out.println("Hi " + loggedInUser.getName());
    }

    public void logoutMessage() {
        boolean keepGoing = true;
        while (keepGoing == true) {
            System.out.println("Do you wish to logout, (y)es or (n)o");
            String inputText = input.nextLine();
            if (inputText.contentEquals("y")) {
                keepGoing = false;
            }
        }
    }

    public void chat() {
        boolean keepGoing = true;
        while (keepGoing) {
            final Socket clientSocket;
            final BufferedReader in;
            final PrintWriter out;
            try {
                clientSocket = new Socket("127.0.0.1", 5000);
                out = new PrintWriter(clientSocket.getOutputStream());                
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // create sender thread
                Thread sender = new Thread(new Runnable() {
                    String msg;

                    @Override
                    public void run() {
                        while (true) {
                            msg = loggedInUser.getName() + ": " + input.nextLine();
                            out.println(msg);
                            out.flush();
                        }
                    }
                });
                sender.start();

                // create receiver thread                
                Thread receiver = new Thread(new Runnable() {
                    String msg;
                    Scanner socketInput = new Scanner(in);

                    @Override
                    public void run() {
                        try {
                            while (true) {
                                msg = socketInput.nextLine();
                                System.out.println("Server : " + msg);
                            }
                        } catch (Exception e) {
                            System.out.println("Server out of service");
                            out.close();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });
                receiver.start();
                receiver.join(); // wait for receiver thread to finish
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Do you wish to logout, (y)es or (n)o");
            String inputText = input.nextLine();
            if (inputText.equals("y")) {
                keepGoing = false;
            }
        }
    }

}