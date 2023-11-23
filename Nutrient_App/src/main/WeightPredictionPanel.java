package src.main;

import org.jfree.data.category.DefaultCategoryDataset;
import src.server.DataServices.ExerciseQueries;
import src.server.DataServices.MealQueries;

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

        LocalDate today = LocalDate.now();
        startDateField = new JTextField(String.valueOf(today));
        startDateField.setEditable(false);
        endDateField = new JTextField(10);
        JButton calculateButton = new JButton("Let's Find out!");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateWeightLoss();
                System.out.println("Calculation Weight Loss Succesfully");
            }
        });

        datePanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        datePanel.add(endDateField);
        datePanel.add(calculateButton);

        return datePanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Curious about how much weight you can lose in the future?");
        resultPanel.add(title);

        resultLabel = new JLabel("Weight Loss Prediction: ");
        resultPanel.add(resultLabel);

        return resultPanel;
    }


    public void calculateWeightLoss() {
        String endDateStr = endDateField.getText();
        String now = LocalDate.now().toString();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
        LocalDate startDate = LocalDate.parse(now, dateFormatter);

        // Call your weight loss calculation method here based on the selected date range
        String weightLoss = calculateWeightLossForDateRange(startDate, endDate);

        // Update the chart dataset
        dataset.clear();
        dataset.addValue(Double.parseDouble(weightLoss), "Weight Loss", "Prediction");

        // Update the result label
        resultLabel.setText("Weight Loss Prediction: " + weightLoss + " kg");
    }

    public String calculateWeightLossForDateRange(LocalDate startDate, LocalDate endDate) {
        // Your weight loss calculation logic here
        long numberDay = startDate.datesUntil(endDate).count();
//        double calorieIntake = ExerciseQueries.getCaloriesIntake(2); // Replace with the actual user id
        double calorieIntake = 0;
        double caloriesBurned = ExerciseQueries.getCaloriesExpended(2); // Replace with the actual user i
        double calorieDeficit = caloriesBurned - calorieIntake;
        double fatLoss = calorieDeficit / 7700;
        double averageCalorieIntake = calorieIntake / MealQueries.getNumOfMeals(2);
        double averageCalorieBurned = caloriesBurned / ExerciseQueries.getNumberOfExercises(2);
        double projectedWeightLoss = fatLoss * numberDay;

        // This is a placeholder method. You should replace it with your actual calculation.
        return String.format("%.2f", projectedWeightLoss); // Replace with the actual weight loss calculation
    }
}