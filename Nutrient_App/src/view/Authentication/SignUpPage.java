package src.view.Authentication;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLIntegrityConstraintViolationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import model.authLogic.SignupLogic;

public class SignUpPage extends JFrame{
	public SignUpPage() {
		userInterface();
	}

	public void userInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setVisible(true);
		signup();
	}

	public void signup() {
		JPanel panel = new JPanel();
		
		// Login prompt
		JLabel title = new JLabel("Create Your Profile");

		// Username label and text field
		JLabel userNLabel = new JLabel("Username:");
		JTextField usernameTF = new JTextField(20);

		// Password label and text field
		JLabel passwordLB = new JLabel("Password:");
		JTextField passwordTF = new JTextField(20);

		// DOB label and text field
		JLabel dobLB = new JLabel("Date of Birth:");
		JTextField dobTF = new JTextField(10);

		// Weight label and text field
		JLabel weightLB = new JLabel("Weight:");
		JTextField weightTF = new JTextField(5);

		// Height label and text field
		JLabel heightLB = new JLabel("Height:");
		JTextField heightTF = new JTextField(5);

		// Submit button
		JButton submitB = new JButton("SUBMIT");

		// sign up
		JLabel signin = new JLabel("Have an account? Sign In");
		signin.addMouseListener((MouseListener) new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				LoginPage login = new LoginPage();
				login.login();
			}
		});
		submitB.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String username = usernameTF.getText();
		        String password = passwordTF.getText(); // Ideally, this should be collected from a JPasswordField
		        String dob = dobTF.getText();

		        try {
		            double weight = Double.parseDouble(weightTF.getText());
		            double height = Double.parseDouble(heightTF.getText());

		            // Try to sign up the user
		            SignupLogic.signUpUser(username, password, dob, weight, height);

		            // If successful, show a success message.
		            JOptionPane.showMessageDialog(SignUpPage.this, "Signed up successfully!");

		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(SignUpPage.this, "Invalid weight or height. Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
		        } catch (SQLIntegrityConstraintViolationException ex) {
		            // If there's an SQL constraint violation (e.g., username uniqueness), show a specific message.
		            JOptionPane.showMessageDialog(SignUpPage.this, "Username already exists. Please choose another.", "Signup Error", JOptionPane.ERROR_MESSAGE);
		        } catch (Exception ex) {
		            // For other exceptions, show a generic error message.
		            JOptionPane.showMessageDialog(SignUpPage.this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
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
		panel.add(dobLB, gbc);

		gbc.gridx = 1;
		panel.add(dobTF, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add(weightLB, gbc);

		gbc.gridx = 1;
		panel.add(weightTF, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		panel.add(heightLB, gbc);

		gbc.gridx = 1;
		panel.add(heightTF, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panel.add(submitB, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panel.add(signin, gbc);

		// Add the panel to the frame
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    add(panel, BorderLayout.CENTER);

		// Validate and repaint the frame
		validate();
		repaint();
	}
}
