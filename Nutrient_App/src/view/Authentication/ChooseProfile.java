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
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Login prompt
        JLabel title = new JLabel("Log In");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Username label and text field
        JLabel userNLabel = new JLabel("Username:");
        JTextField usernameTF = new JTextField(20);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(userNLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameTF, gbc);

        // Password label and password field
        JLabel passwordLB = new JLabel("Password:");
        JPasswordField passwordPF = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLB, gbc);
        gbc.gridx = 1;
        panel.add(passwordPF, gbc);

        // Login button with action listener
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            // TODO: Implement login logic here
            String username = usernameTF.getText();
            char[] password = passwordPF.getPassword();
            // Authenticate user
            // You should hash the password and compare it with stored hashed passwords
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

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
