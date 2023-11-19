package src.client.LogData;

import javax.swing.*;

import src.server.DataServices.ExerciseQueries;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ExercisePanel extends JPanel {
    private JTextField durationField, otherExerciseField;
    private JComboBox<String> intensityCombo;
    private JComboBox<String> exerciseField;
    private S12FocusLost autoSuggest;
    private List<String> exerciseList = ExerciseQueries.getExerciseList();

    public ExercisePanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Log Exercise"));
        addExerciseFields();
    }

    private void addExerciseFields() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Exercise Type
        add(new JLabel("Exercise Type:"), gbc);
        exerciseList.add("Other");
        JComboBox temp = new JComboBox<>(exerciseList.toArray());
        autoSuggest = new S12FocusLost(temp);
        exerciseField = temp;
        gbc.gridx = 1;
        exerciseField.setName("Exercise");
        add(exerciseField, gbc);
        exerciseField.setPreferredSize(new Dimension(200, 30));

        // JTextField for manual entry
        gbc.gridx = 1;
        gbc.gridy++;
        otherExerciseField = new JTextField("Manually input your activity");
        otherExerciseField.setName("OtherExercise");
        otherExerciseField.setForeground(Color.GRAY);
        otherExerciseField.setPreferredSize(new Dimension(200, 30));
        otherExerciseField.setVisible(false); // initially hidden
        add(otherExerciseField, gbc);

        // Add an ActionListener to exerciseField
        exerciseField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedExercise = (String) exerciseField.getSelectedItem();
                if ("Other".equals(selectedExercise)) {
                    otherExerciseField.setVisible(true); // show the JTextField for manual entry
                } else {
                    otherExerciseField.setVisible(false); // hide the JTextField
                }
            }
        });

        otherExerciseField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if ("Manually input your activity".equals(otherExerciseField.getText())) {
                    otherExerciseField.setText("");
                    otherExerciseField.setForeground(Color.BLACK);
                }
            }
        
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (otherExerciseField.getText().isEmpty()) {
                    otherExerciseField.setForeground(Color.GRAY);
                    otherExerciseField.setText("Manually input your activity");
                }
            }
        });

        // Duration
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Duration:"), gbc);
        durationField = new JTextField();
        gbc.gridx = 1;
        durationField.setName("Duration");
        add(durationField, gbc);
        durationField.setPreferredSize(new Dimension(200, 30));

        // Intensity
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Intensity:"), gbc);
        intensityCombo = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        intensityCombo.setName("Intensity");
        gbc.gridx = 1;
        add(intensityCombo, gbc);
        intensityCombo.setPreferredSize(new Dimension(100, 30));
    }

    public JComboBox<String> getExerciseTypeField() {
        return exerciseField;
    }

    public JTextField getDurationField() {
        return durationField;
    }

    public JComboBox<String> getIntensityCombo() {
        return intensityCombo;
    }
}


