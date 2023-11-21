package src.main;

import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class WeightPredictionPanel extends JPanel {

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private JTextField startDateField;
    private JTextField endDateField;
    private JLabel resultLabel;

    public WeightPredictionPanel() {
        createPanel();
    }

    private void createPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 300));

        JPanel resultPanel = createResultPanel();
        add(resultPanel);

        JPanel datePanel = createDatePanel();
        add(datePanel);
    }

    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        startDateField = new JTextField(String.valueOf(Calendar.getInstance()));
        startDateField.setEditable(false);
        endDateField = new JTextField(10);
        JButton calculateButton = new JButton("Let's Find out!");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateWeightLoss();
            }
        });

        datePanel.add(new JLabel("End Date (yyyy-MM-dd):"));
        datePanel.add(endDateField);
        datePanel.add(calculateButton);

        return datePanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel title = new JLabel("Curious about how much weight you can lose in a given time frame?");
        add(title);

        JLabel title2 = new JLabel("Enter the date in the future below to find out!");
        add(title2);

        resultLabel = new JLabel("Weight Loss Prediction: ");
        resultPanel.add(resultLabel);
        resultPanel.add(title);
        resultPanel.add(title2);

        return resultPanel;
    }

    public void calculateWeightLoss() {
        String endDateStr = endDateField.getText();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);

        // Call your weight loss calculation method here based on the selected date range
        double weightLoss = calculateWeightLossForDateRange(Calendar.getInstance(), endDate);

        // Update the chart dataset
        dataset.clear();
        dataset.addValue(weightLoss, "Weight Loss", "Prediction");

        // Update the result label
        resultLabel.setText("Weight Loss Prediction: " + weightLoss + " kg");
    }

    private double calculateWeightLossForDateRange(Calendar startDate, LocalDate endDate) {
        // Your weight loss calculation logic here
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = startDate.toString();
        String endDateStr = endDate.toString();
        LocalDateTime date1 = LocalDateTime.from(LocalDate.parse(today, DateTimeFormatter.ISO_LOCAL_DATE));
        LocalDateTime date2 = LocalDateTime.from(LocalDate.parse(endDateStr, dtf));
        int numberDay = (int) Duration.between(date1, date2).toDays();
        double calorieIntake = 0.0; // Replace with the actual calorie intake calculation
        double caloriesBurned = 0.0; // Replace with the actual calories burned calculation
        double calorieDeficit = caloriesBurned - calorieIntake;
        double fatLoss = calorieDeficit / 7700;
        double projectedWeightLoss = fatLoss * numberDay;

        // This is a placeholder method. You should replace it with your actual calculation.
        return projectedWeightLoss; // Replace with the actual weight loss calculation
    }
}
