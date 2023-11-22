package src.client.LogData;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends JPanel {
    private JTextArea historyTextArea;

    public HistoryPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("History"));

        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);

        JScrollPane historyScrollPane = new JScrollPane(historyTextArea);
        historyScrollPane.setPreferredSize(new Dimension(980, 100));

        add(historyScrollPane, BorderLayout.CENTER);
    }

    public JTextArea getHistoryTextArea() {
        return historyTextArea;
    }

    public void appendToHistory(String entry) {
        historyTextArea.append(entry + "\n");
        historyTextArea.setCaretPosition(historyTextArea.getDocument().getLength());
    }
}
