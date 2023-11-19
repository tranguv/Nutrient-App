package src.client.LogData;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DatePanel extends JPanel {
    private String selectedDate;
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public DatePanel() {
        setLayout(new GridBagLayout());
        addDateFields();
    }

    private void addDateFields() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Date Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Date (yyyy-MM-dd):"), gbc);

        // Date Field
        gbc.gridx++;
        JFormattedTextField dateField = new JFormattedTextField(df);
        dateField.setPreferredSize(new Dimension(200, 30));

        // Update selectedDate when user changes input
        dateField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                try {
                    df.setLenient(false);
                    df.parse(dateField.getText());
                    selectedDate = dateField.getText();
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(DatePanel.this, "Please enter a valid date in the format yyyy-MM-dd.");
                }
            }
        });

        add(dateField, gbc);
    }

    public String getSelectedDate() {
        return selectedDate;
    }
}
