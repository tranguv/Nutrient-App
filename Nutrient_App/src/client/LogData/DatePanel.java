package src.client.LogData;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Date;
//import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePanel extends JPanel {
    private String selectedDate;
    private JLabel dateLabel; // Reference to the date label
    private JFormattedTextField dateField; // Reference to the date field

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

    public void setSelectedDate(String text) {
        this.selectedDate = text;
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
        dateField = new JFormattedTextField(df);
        dateField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateDate();
            }
            public void removeUpdate(DocumentEvent e) {
                updateDate();
            }
            public void insertUpdate(DocumentEvent e) {
                updateDate();
            }

            private void updateDate() {
                selectedDate = dateField.getText();
            }
        });

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

        // Set size for date field
        dateField.setPreferredSize(new Dimension(200, 30));
        add(dateField, gbc);
    }

    public String getSelectedDate() {
        return selectedDate;
    }
    public LocalDate getSelectedLocalDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sel_date = getSelectedDate();
        LocalDate sel_local_date = LocalDate.parse(sel_date, dateFormatter);
        return sel_local_date;
    }

    public Date getSelectedDate_Date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            String d1 = getSelectedDate();
            date = (Date) dateFormat.parse(d1);
            System.out.println(getSelectedDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
