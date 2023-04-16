// WelcomePage-klassen definerer brugerens velkomstside, hvor brugeren kan chatte og logge ud. Klassen opretter en GUI og opdaterer GUI'en
// ved at skrive og modtage beskeder.

package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class WelcomePage {
    // Private instansvariabler til den bruger, der er logget ind, en scanner til input, og en BufferedReader og PrintWriter til at kommunikere med serveren.
    private User loggedInUser;
    private Scanner input = new Scanner(System.in);
    private BufferedReader in;
    private PrintWriter out;
    // Private instansvariabler til GUI'en.
    private JTextField textField;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    /**
     * Action listener for text field
     */
    private final class ActionListenerImplementation implements ActionListener {

        // @Override is not used, since we are implementing an interface method.
        public void actionPerformed(ActionEvent e) {
            // Get the text from text field
            String inputText = textField.getText();

            // send text to server
            out.println(inputText);
            out.flush();

            // Clear the text field
            textField.setText("");
            textField.requestFocus();
        }
    }

    // Konstruktør, der initialiserer den bruger, der er logget ind.
    public WelcomePage(User loggingInUser) {
        this.loggedInUser = loggingInUser;
    }

    // Metode, der opretter GUI'en.
    void createGui() {
        // Opret et JFrame-objekt med titlen "BabelChat".
        JFrame frame = new JFrame("BabelChat");

        // Sæt størrelsen af vinduet.
        frame.setSize(900, 600);

        // Luk vinduet, når brugeren lukker det.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Opret et JTextArea-objekt og et JTextField-objekt.
        textArea = new JTextArea();
        textField = new JTextField();

        // Opret et JScrollPane-objekt og tilføj JTextArea-objektet til det.
        scrollPane = new JScrollPane(textArea);

        // Sæt JTextArea-objektet til ikke at være redigerbart.
        textArea.setEditable(false);

        // Opret et JPanel-objekt med GridBagLayout.
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();

        // Sæt de forskellige layout-egenskaber for JPanel-objektet.
        c1.gridwidth = GridBagConstraints.REMAINDER;
        c1.anchor = GridBagConstraints.NORTH;
        c1.fill = GridBagConstraints.BOTH;
        c1.weightx = 1.0;
        c1.weighty = 0.99;
        panel.add(scrollPane, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.anchor = GridBagConstraints.SOUTH;
        c2.weightx = 1.0;
        c2.weighty = 0.01;
        c2.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, c2);

        // Tilføj JPanel-objektet til JFrame-objektet.
        frame.add(panel);

        // Opret en action listener til text field.
        ActionListener textFieldListener = new ActionListenerImplementation();

        // Tilføj action listener til JTextField-objektet.
        textField.addActionListener(textFieldListener);

        // Vis JFrame-objektet.
        frame.setVisible(true);
    }
    public void outputMessage() {
        // Method to output a message to the console
        System.out.println("Hi " + loggedInUser.getName());
    }

    public void logoutMessage() {
        // Method to prompt the user to logout
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
        // Method to start a chat session
        createGui();
        boolean keepGoing = true;
        while (keepGoing) {
            final Socket clientSocket;

            try {
                // Create a socket for connecting to the chat server
                clientSocket = new Socket("127.0.0.1", 5000);
                // Create a PrintWriter for sending messages to the server
                out = new PrintWriter(clientSocket.getOutputStream());
                // Create a BufferedReader for receiving messages from the server
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // create sender thread
                Thread sender = new Thread(new Runnable() {
                    String msg;

                    @Override
                    public void run() {
                        // Send messages to the server
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
                            // Receive messages from the server
                            while (true) {
                                msg = socketInput.nextLine();
                                textArea.append(msg + "\n");
                                textArea.setCaretPosition(textArea.getDocument().getLength()); // scroll to bottom
                            }
                        } catch (Exception e) {
                            // Handle errors that occur when receiving messages
                            textArea.append("Server out of service\n");
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
                // Handle errors that occur when creating the socket
                e.printStackTrace();
            } catch (InterruptedException e) {
                // Handle errors that occur when the receiver thread is interrupted
                e.printStackTrace();
            }
            // Prompt the user to logout
            System.out.println("Do you wish to logout, (y)es or (n)o");
            String inputText = input.nextLine();
            if (inputText.equals("y")) {
                keepGoing = false;
            }
        }
    }
}

// Metode, der skriver en besked
