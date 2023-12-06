package src.main;

import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeightPredictionPanel extends JPanel {
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private JLabel resultLabel;

    public WeightPredictionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 300));

        add(createResultPanel());
        add(createDatePanel());
    }

    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        LocalDate today = LocalDate.now();
        JTextField startDateField = createUneditableTextField(String.valueOf(today));
        JTextField endDateField = new JTextField(10);

        JButton calculateButton = createCalculateButton(startDateField, endDateField);

        datePanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        datePanel.add(endDateField);
        datePanel.add(calculateButton);

        return datePanel;
    }

    private JButton createCalculateButton(JTextField startDateField, JTextField endDateField) {
        JButton calculateButton = new JButton("Let's Find out!");
        calculateButton.addActionListener(e -> {
            calculateWeightLoss(startDateField, endDateField);
            System.out.println("Calculation Weight Loss Successfully");
        });
        return calculateButton;
    }

    private JTextField createUneditableTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setEditable(false);
        return textField;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Curious about how much weight you can lose in the future?");
        resultLabel = new JLabel("Weight Loss Prediction: ");

        resultPanel.add(title);
        resultPanel.add(resultLabel);

        return resultPanel;
    }

    private void calculateWeightLoss(JTextField startDateField, JTextField endDateField) {
        String endDateStr = endDateField.getText();
        String now = LocalDate.now().toString();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
        LocalDate startDate = LocalDate.parse(now, dateFormatter);

        // Call your weight loss calculation method here based on the selected date range
        String weightLoss = WeightLossCalculation.calculateWeightLossForDateRange(startDate, endDate);

        // Update the chart dataset
        updateChartDataset(Double.parseDouble(weightLoss));

        // Update the result label
        resultLabel.setText("Weight Loss Prediction: " + weightLoss + " kg");
    }

    private void updateChartDataset(double value) {
        dataset.clear();
        dataset.addValue(value, "Weight Loss", "Prediction");
    }
}
