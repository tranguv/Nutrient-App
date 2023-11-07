package src.view.Authentication;

import javax.swing.*;
import java.awt.*;

public class ChooseProfile extends JFrame {

    public ChooseProfile() {
        userInterface();
    }

    private void userInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window
        chooseProfile();
        setVisible(true);
    }

    private void chooseProfile() {
        // Panel setup
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Login prompt
        JLabel title = new JLabel("User Profile");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Nickname label and text field
        JLabel nickNameLabel = new JLabel("Nickname:");
        JTextField nickNameTF = new JTextField(20);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(nickNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nickNameTF, gbc);

        // Age label and text field
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageTF = new JTextField(5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ageLabel, gbc);
        gbc.gridx = 1;
        panel.add(ageTF, gbc);

        // Gender label and combo box
        JLabel genderLabel = new JLabel("Gender:");
        JComboBox<String> genderCB = new JComboBox<>(new String[] {"Male", "Female", "Other"});
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(genderLabel, gbc);
        gbc.gridx = 1;
        panel.add(genderCB, gbc);

        // Weight to Lose label and text field
        JLabel weightToLoseLabel = new JLabel("Weight to Lose (lbs):");
        JTextField weightToLoseTF = new JTextField(5);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(weightToLoseLabel, gbc);
        gbc.gridx = 1;
        panel.add(weightToLoseTF, gbc);

        // Submit button with action listener
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Here you should implement the logic to handle the user profile
            String nickname = nickNameTF.getText();
            String age = ageTF.getText(); // Validate that this is a number
            String gender = (String) genderCB.getSelectedItem();
            String weightToLose = weightToLoseTF.getText(); // Validate that this is a number
            // Process the user profile information
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        // Validate and repaint the frame
        validate();
        repaint();
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChooseProfile();
        });
    }
}
