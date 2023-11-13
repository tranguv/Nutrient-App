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
        setSize(1000, 600);
        setLocationRelativeTo(null); // Center the window
        chooseProfile();
        setVisible(true);
    }

    private void chooseProfile() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Login prompt
        JLabel title = new JLabel("Create Profile");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // First Name label and text field
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameTF = new JTextField(20);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(firstNameTF, gbc);

        // Last Name label and text field
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameTF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(lastNameTF, gbc);

        // Gender label and combo box
        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderCB = new JComboBox<>(genders);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(genderLabel, gbc);
        gbc.gridx = 1;
        panel.add(genderCB, gbc);

        // Submit button with action listener
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // TODO: Implement profile creation logic here
            String firstName = firstNameTF.getText();
            String lastName = lastNameTF.getText();
            String gender = (String) genderCB.getSelectedItem();
            // Handle profile creation
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
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
