package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RegLogSysDriver {
    
    /**
     * Action listener for text field
     */
    private final class ActionListenerImplementation implements ActionListener {
        //private Scanner input = new Scanner(System.in);
                
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the text from the JTextField
            String inputText = textField.getText();
      
            // add text to text area
            textArea.append(inputText + "\n");
            System.out.println(inputText);
            
            // Clear the JTextField
            textField.setText("");
            textField.requestFocus();
        }
    }


    JTextField textField; 
    JTextArea textArea;
    JScrollPane scrollPane;

    public RegLogSysDriver() {

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

        // add some initial text
        textArea.setText("Added text will shown here.");
        textField.setText("Add text here...");

        // create listener
        ActionListener textFieldListener = new ActionListenerImplementation();
        
        // Add the ActionListener to the JTextField
        textField.addActionListener(textFieldListener);        

        // show frame
        frame.setVisible(true);
}        


    public static void main(String[] args) {
        RegLogSysDriver gui = new RegLogSysDriver();
        RegLogSystem system = new RegLogSystem();

    // Run the registration/login system
    system.run();

    }
}
