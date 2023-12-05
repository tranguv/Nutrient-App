//package src.main;
//
//import org.jfree.data.category.DefaultCategoryDataset;
//import src.model.MainApplication;
//import src.server.DataServices.DailyNutrientIntakeViz;
//import src.server.DataServices.ExerciseQueries;
//import src.server.DataServices.MealQueries;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.Date;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Calendar;
//import java.util.List;
//
//public class WeightPredictionPanel extends JPanel {
//
//    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//    private JTextField startDateField;
//    private JTextField endDateField;
//    private JLabel resultLabel;
//
//    public WeightPredictionPanel() {
//        createPanel();
//    }
//
//    private void createPanel() {
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        setPreferredSize(new Dimension(300, 300));
//
//        JPanel resultPanel = createResultPanel();
//        add(resultPanel);
//
//        JPanel datePanel = createDatePanel();
//        add(datePanel);
//    }
//
//    private JPanel createDatePanel() {
//        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//
//        LocalDate today = LocalDate.now();
//        startDateField = new JTextField(String.valueOf(today));
//        startDateField.setEditable(false);
//        endDateField = new JTextField(10);
//        JButton calculateButton = new JButton("Let's Find out!");
//        calculateButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                calculateWeightLoss();
//                System.out.println("Calculation Weight Loss Succesfully");
//            }
//        });
//
//        datePanel.add(new JLabel("End Date (YYYY-MM-DD):"));
//        datePanel.add(endDateField);
//        datePanel.add(calculateButton);
//
//        return datePanel;
//    }
//
//    private JPanel createResultPanel() {
//        JPanel resultPanel = new JPanel();
//        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
//
//        JLabel title = new JLabel("Curious about how much weight you can lose in the future?");
//        resultPanel.add(title);
//
//        resultLabel = new JLabel("Weight Loss Prediction: ");
//        resultPanel.add(resultLabel);
//
//        return resultPanel;
//    }
//
//
//    public void calculateWeightLoss() {
//        String endDateStr = endDateField.getText();
//        String now = LocalDate.now().toString();
//
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
//        LocalDate startDate = LocalDate.parse(now, dateFormatter);
//
//        // Call your weight loss calculation method here based on the selected date range
//        String weightLoss = WeightLossCalculation.calculateWeightLossForDateRange(startDate, endDate);
//
//        // Update the chart dataset
//        dataset.clear();
//        dataset.addValue(Double.parseDouble(weightLoss), "Weight Loss", "Prediction");
//
//        // Update the result label
//        resultLabel.setText("Weight Loss Prediction: " + weightLoss + " kg");
//    }
//}

package src.main;

import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeightPredictionPanel extends JPanel {

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private JTextField startDateField;
    private JTextField endDateField;
    private JLabel resultLabel;

    public WeightPredictionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 300));

        JPanel resultPanel = createResultPanel();
        JPanel datePanel = createDatePanel();

        add(resultPanel);
        add(datePanel);
    }

    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        LocalDate today = LocalDate.now();
        startDateField = createUneditableTextField(String.valueOf(today));
        endDateField = new JTextField(10);

        JButton calculateButton = createCalculateButton();

        datePanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        datePanel.add(endDateField);
        datePanel.add(calculateButton);

        return datePanel;
    }

    private JButton createCalculateButton() {
        JButton calculateButton = new JButton("Let's Find out!");
        calculateButton.addActionListener(e -> {
            calculateWeightLoss();
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

    public void calculateWeightLoss() {
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
