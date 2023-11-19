package src.client.LogData;

import javax.swing.*;
import java.awt.*;

public class ExercisePanel extends JPanel {
    private JTextField exerciseTypeField, durationField;
    private JComboBox<String> intensityCombo;

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
        exerciseTypeField = new JTextField();
        gbc.gridx = 1;
        add(exerciseTypeField, gbc);
        exerciseTypeField.setPreferredSize(new Dimension(200, 30));

        // Duration
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Duration:"), gbc);
        durationField = new JTextField();
        gbc.gridx = 1;
        add(durationField, gbc);
        durationField.setPreferredSize(new Dimension(200, 30));

        // Intensity
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Intensity:"), gbc);
        intensityCombo = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        gbc.gridx = 1;
        add(intensityCombo, gbc);
        intensityCombo.setPreferredSize(new Dimension(100, 30));
    }

    public JTextField getExerciseTypeField() {
        return exerciseTypeField;
    }

    public JTextField getDurationField() {
        return durationField;
    }

    public JComboBox<String> getIntensityCombo() {
        return intensityCombo;
    }
}


