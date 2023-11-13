package src.client.Authentication;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.EventListener;

import javax.swing.*;

import src.client.Dashboard;

public class LoginPage extends JFrame {
	public LoginPage() {
		userInterface();
	}

	public void userInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setVisible(true);
		login();
	}

	public void login() {
		JPanel panel = new JPanel();

		// Login prompt
		JLabel title = new JLabel("Log In");

		// Username label and text field
		JLabel userNLabel = new JLabel("Username:");
		JTextField usernameTF = new JTextField(20);

		// Password label and text field
		JLabel passwordLB = new JLabel("Password:");
		JTextField passwordTF = new JTextField(20);

		// Submit button
		JButton submitB = new JButton("SUBMIT");
		submitB.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String username = usernameTF.getText();
		        String password = passwordTF.getText(); // Ideally, this should be collected from a JPasswordField

				if (username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(LoginPage.this, "Username and password cannot be empty!");
				}

		        try {
		            // Try to log in the user
		            boolean isValidUser = src.server.DataServices.DBQueries.validateUser(username, password);

		            // If successful, show a success message.
					if (isValidUser) {
						Dashboard dashboard = new Dashboard();
					} else {
						JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password!");
					}

		        } catch (Exception ex) {
		            // For other exceptions, show a generic error message.
		            JOptionPane.showMessageDialog(LoginPage.this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

		// sign up
		JLabel signup = new JLabel("Don't have account? Sign Up");
		signup.addMouseListener((MouseListener) new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				SignUpPage signup = new SignUpPage();
				signup.signup();
			}
		});


		// styling
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Calibri", Font.BOLD, 30));

		// Set up the layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		// Add components to the panel
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panel.add(title, gbc);

		gbc.gridwidth = 1;
		gbc.gridy = 1;
		panel.add(userNLabel, gbc);

		gbc.gridx = 1;
		panel.add(usernameTF, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(passwordLB, gbc);

		gbc.gridx = 1;
		panel.add(passwordTF, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panel.add(submitB, gbc);

		gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(signup, gbc);

		// Add the panel to the frame
		add(panel, BorderLayout.CENTER);

		// Validate and repaint the frame
		validate();
		repaint();
	}

	public void loginValidation(java.awt.event.ActionEvent evt) {
		
	}
}
