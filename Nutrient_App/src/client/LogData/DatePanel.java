package src.client.LogData;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DatePanel extends JPanel {
    private String selectedDate;

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
        add(new JLabel("Date:"), gbc);

        // Date Field
        gbc.gridx++;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField dateField = new JFormattedTextField(df);

        // Validate date input format
        dateField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SLASH))) {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid");
                    e.consume();
                }
            }
        });

        this.selectedDate = dateField.getText();
        // Set size for date field
        dateField.setPreferredSize(new Dimension(200, 30));
        add(dateField, gbc);
    }

    public String getSelectedDate() {
        return selectedDate;
    }
}


