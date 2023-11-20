package src.client.Authentication;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLIntegrityConstraintViolationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import src.main.CombinedChartsPanel;
import src.model.User;
import src.server.DataServices.UserQueries;

public class SignUpPage extends JFrame{
	public SignUpPage() {
		userInterface();
	}

	public void userInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
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
		JPasswordField passwordPF = new JPasswordField(20);

		// DOB label and text field
		JLabel dobLB = new JLabel("Date of Birth (dd/mm/yy):");

		JComboBox<String> dayComboBox = new JComboBox<>();
		for (int day = 1; day <= 31; day++) {
			dayComboBox.addItem(String.valueOf(day));
		}

		JComboBox<String> monthComboBox = new JComboBox<>();
		for (int month = 1; month <= 12; month++) {
			monthComboBox.addItem(String.valueOf(month));
		}

		JComboBox<String> yearComboBox = new JComboBox<>();
		for (int year = 1950; year <= 2030; year++) {
			yearComboBox.addItem(String.valueOf(year));
		}



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
				char[] password = passwordPF.getPassword(); // Ideally, this should be collected from a JPasswordField
				String day = (String) dayComboBox.getSelectedItem();
				String month = (String) monthComboBox.getSelectedItem();
				String year = (String) yearComboBox.getSelectedItem();
				String dob = year + "-" + month + "-" + day;

				try {
					double weight = Double.parseDouble(weightTF.getText());
					double height = Double.parseDouble(heightTF.getText());

					// Try to sign up the user
					// SignupLogic.signUpUser(username, password, dob, weight, height);
					if (UserQueries.validateUser(username, String.valueOf(password))) {
						JOptionPane.showMessageDialog(SignUpPage.this, "Username already exists. Please choose another.", "Signup Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					User newUser = new User(username, String.valueOf(password), "", "", "", dob, weight, height, "metric");

					// Move the createUser method call into the try block
					if (UserQueries.createUser(newUser)) {
						newUser.setId(UserQueries.getUserID());
						JOptionPane.showMessageDialog(SignUpPage.this, "Signed up successfully!");
						new CombinedChartsPanel("blabla",newUser).execute();
						dispose();

					} else {
						JOptionPane.showMessageDialog(SignUpPage.this, "Username already exists. Please choose another.", "Signup Error", JOptionPane.ERROR_MESSAGE);
						System.out.println("User signed up unsuccessfully!");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(SignUpPage.this, "Invalid weight or height. Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
				} catch (SQLIntegrityConstraintViolationException ex) {
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
		panel.add(passwordPF, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(dobLB, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(dobLB, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 1; // Reset gridwidth to 1 for individual components
		panel.add(dayComboBox, gbc);

		gbc.gridx = 2; // Adjust gridx for monthComboBox
		panel.add(monthComboBox, gbc);

		gbc.gridx = 3; // Adjust gridx for yearComboBox
		panel.add(yearComboBox, gbc);

// Reset gridwidth for subsequent components
		gbc.gridwidth = 1;

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