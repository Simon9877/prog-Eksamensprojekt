package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class WelcomePage {
    private User loggedInUser;
    private Scanner input = new Scanner(System.in);
    private BufferedReader in;
    private PrintWriter out;
    private JTextField textField; 
    private JTextArea textArea;
    private JScrollPane scrollPane;

    /**
     * Action listener for text field
     */
    private final class ActionListenerImplementation implements ActionListener {
        //private Scanner input = new Scanner(System.in);
                
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // Get the text from text field
            String inputText = textField.getText();
      
            // send text to server
            //textArea.append(inputText + "\n");
            out.println(inputText);
            out.flush();
            //System.out.println(inputText);
            
            // Clear the text field 
            textField.setText("");
            textField.requestFocus();
        }
    }

    public WelcomePage(User loggingInUser) {
        this.loggedInUser = loggingInUser;
    }

    void createGui() {

        JFrame frame = new JFrame("BabelChat");
        // Set the title of the window
        frame.setTitle("BabelChat");

        // Set the size of the window
        frame.setSize(900, 600);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new JTextArea object
        textArea = new JTextArea();
        textField = new JTextField();
       
        // create scroll pane
        scrollPane = new JScrollPane(textArea);

        // Make not ediable
        textArea.setEditable(false);

        // Add the text field and scroll pane to the panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();

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

        // add panel to frame
        frame.add(panel);

        // create listener
        ActionListener textFieldListener = new ActionListenerImplementation();
        
        // Add the ActionListener to the JTextField
        textField.addActionListener(textFieldListener);        

        // show frame
        frame.setVisible(true);
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
        createGui();
        boolean keepGoing = true;
        while (keepGoing) {
            final Socket clientSocket;

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
                                textArea.append(msg + "\n");
                                textArea.setCaretPosition(textArea.getDocument().getLength()); // scroll to bottom
                            }
                        } catch (Exception e) {
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