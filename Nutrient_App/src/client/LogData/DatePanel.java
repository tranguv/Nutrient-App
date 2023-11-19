package src.client.LogData;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DatePanel extends JPanel {
    private String selectedDate;
    private JLabel dateLabel; // Reference to the date label

    public DatePanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(""));
        addDateFields();
    }

    public void setDate(String text) {
        if (dateLabel != null) {
            dateLabel.setText(text);
        }
    }

    private void addDateFields() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Date Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        dateLabel = new JLabel("Date:"); // Initialize the date label
        add(dateLabel, gbc);

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
                        (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_MINUS))) {
                    JOptionPane.showMessageDialog(null, "Please Enter YYYY-MM-DD Format");
                    e.consume();
                }
            }
        });

        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                selectedDate = dateField.getText();
                System.out.println(selectedDate);
            }
        });

        // Set size for date field
        dateField.setPreferredSize(new Dimension(200, 30));
        add(dateField, gbc);
    }

    public String getSelectedDate() {
        return selectedDate;
    }
}
