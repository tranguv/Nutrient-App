package src.client.Authentication;

import src.model.User;
import src.server.DataServices.UserQueries;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ChooseProfile extends JFrame {
    User user;


    public ChooseProfile(User newUser) {
        this.user = newUser;


        userInterface();
    }

    public void userInterface() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setSize(1000, 600);
        setLocationRelativeTo(null);
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

        // User Name label and text field
        JLabel userNameLabel = new JLabel("User Name:");
        JTextField userNameTF = new JTextField(20);
        userNameTF.setText(this.user.getUsername());
        userNameTF.setEditable(false);
        userNameTF.setBackground(Color.LIGHT_GRAY); // Set a different background color
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(userNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(userNameTF, gbc);

        // First Name label and text field
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameTF = new JTextField(20);
        if ( !this.user.getFirstName().isBlank()){
            firstNameTF.setText(this.user.getFirstName());
        }
        firstNameTF.setEditable(false);
        gbc.gridy = 2; // Changed gridy to 2
        gbc.gridx = 0;
        panel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(firstNameTF, gbc);

        // Last Name label and text field
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameTF = new JTextField(20);
        if ( !this.user.getLastName().isBlank()){
            lastNameTF.setText(this.user.getLastName());
        }
        lastNameTF.setEditable(false);
        gbc.gridy = 3; // Changed gridy to 3
        gbc.gridx = 0;
        panel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(lastNameTF, gbc);

        // Gender label and combo box
        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"M", "F"};
        JComboBox<String> genderCB = new JComboBox<>(genders);
        if ( !this.user.getSex().isBlank()){
            genderCB.setSelectedItem(this.user.getSex());
        }
        genderCB.setEditable(false);
        gbc.gridy = 4; // Changed gridy to 4
        gbc.gridx = 0;
        panel.add(genderLabel, gbc);
        gbc.gridx = 1;
        panel.add(genderCB, gbc);

        // Date of Birth label and text field
        JLabel dobLabel = new JLabel("Date of Birth (yyyy-mm-dd):");
        JTextField dobTF = new JTextField(20);
        dobTF.setText(this.user.getDateOfBirth());
        genderCB.setEditable(false);
        gbc.gridy = 5; // Changed gridy to 5
        gbc.gridx = 0;
        panel.add(dobLabel, gbc);
        gbc.gridx = 1;
        panel.add(dobTF, gbc);

        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightTF = new JTextField(20);
        if (this.user.getHeight() != 0){
            heightTF.setText(String.valueOf(this.user.getHeight())); // Convert to String
        }
        heightTF.setEditable(false);
        heightTF.setBackground(Color.LIGHT_GRAY);
        gbc.gridy = 6; // Update gridy for height
        gbc.gridx = 0;
        panel.add(heightLabel, gbc);
        gbc.gridx = 1;
        panel.add(heightTF, gbc);

// Weight label and text field
        JLabel weightLabel = new JLabel("Weight (kg):");
        JTextField weightTF = new JTextField(20);
        if (this.user.getWeight() != 0){
            weightTF.setText(String.valueOf(this.user.getWeight())); // Convert to String
        }
        weightTF.setEditable(false);
        weightTF.setBackground(Color.LIGHT_GRAY);
        gbc.gridy = 7; // Update gridy for weight
        gbc.gridx = 0;
        panel.add(weightLabel, gbc);
        gbc.gridx = 1;
        panel.add(weightTF, gbc);

        lastNameTF.setBackground(Color.LIGHT_GRAY);
        firstNameTF.setBackground(Color.LIGHT_GRAY);
        dobTF.setBackground(Color.LIGHT_GRAY);
        genderCB.setBackground(Color.LIGHT_GRAY);
        weightTF.setBackground(Color.lightGray);
        heightTF.setBackground(Color.lightGray);
        // Submit button with action listener
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Profile submission logic
            String firstName = firstNameTF.getText();
            String lastName = lastNameTF.getText();
            String gender = (String) genderCB.getSelectedItem();
            String dob = dobTF.getText();
            double height = Double.parseDouble(heightTF.getText());
            double weight = Double.parseDouble(weightTF.getText());
            UserQueries find = new UserQueries();
            dob = dobTF.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Ensure strict parsing

            try {
                dateFormat.parse(dob); // Validate the date format
                // Continue with your submission logic
                // ...
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.");
                dobTF.requestFocusInWindow(); // Focus back on the date field
                return; // Stop further processing
            }
            find.updateUserDetails(user.getUsername(), firstName, lastName, gender, dob,height, weight);
            this.user.setFirstName(firstName);
            this.user.setLastName(lastName);
            this.user.setSex(gender);
            this.user.setHeight(height);
            this.user.setWeight(weight);
            this.user.setDateOfBirth(dob);
            dispose();
        });



// Edit button
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            // Enable editing of profile
            lastNameTF.setEditable(true);
            firstNameTF.setEditable(true);
            weightTF.setEditable(true);
            dobTF.setEditable(true);
            heightTF.setEditable(true);
            dobTF.setBackground(Color.white);
            lastNameTF.setBackground(Color.white);
            firstNameTF.setBackground(Color.white);
            weightTF.setBackground(Color.white);
            heightTF.setBackground(Color.white);
            genderCB.setBackground(Color.white);
            // Custom handling for JComboBox background if needed
        });

        gbc.gridy = 8; // Update gridy for submitButton
        panel.add(submitButton, gbc);

        gbc.gridy = 9; // Update gridy for editButton
        panel.add(editButton, gbc);



        add(panel, BorderLayout.CENTER);
        validate();
        repaint();
    }


}
