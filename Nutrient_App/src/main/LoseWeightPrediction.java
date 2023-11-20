package src.main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoseWeightPrediction extends ApplicationFrame {

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private JTextField startDateField;
    private JTextField endDateField;
    private JLabel resultLabel;

    public LoseWeightPrediction(String title) {
        super(title);

        JPanel mainPanel = createMainPanel();
        setContentPane(mainPanel);

        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel datePanel = createDatePanel();
        mainPanel.add(datePanel);

        JPanel chartPanel = createChartPanel();
        mainPanel.add(chartPanel);

        JPanel resultPanel = createResultPanel();
        mainPanel.add(resultPanel);

        return mainPanel;
    }

    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        startDateField = new JTextField(10);
        endDateField = new JTextField(10);
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateWeightLoss();
            }
        });

        datePanel.add(new JLabel("Start Date (yyyy-MM-dd):"));
        datePanel.add(startDateField);
        datePanel.add(new JLabel("End Date (yyyy-MM-dd):"));
        datePanel.add(endDateField);
        datePanel.add(calculateButton);

        return datePanel;
    }

    private JPanel createChartPanel() {
        JFreeChart chart = ChartFactory.createBarChart(
                "Weight Loss Prediction", "Day", "Weight Loss (kg)", dataset,
                PlotOrientation.VERTICAL, true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));

        JPanel panel = new JPanel();
        panel.add(chartPanel);

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultLabel = new JLabel("Weight Loss Prediction: ");
        resultPanel.add(resultLabel);

        return resultPanel;
    }

    private void calculateWeightLoss() {
        String startDateStr = startDateField.getText();
        String endDateStr = endDateField.getText();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            // Call your weight loss calculation method here based on the selected date range
            double weightLoss = calculateWeightLossForDateRange(startDate, endDate);

            // Update the chart dataset
            dataset.clear();
            dataset.addValue(weightLoss, "Weight Loss", "Prediction");

            // Update the result label
            resultLabel.setText("Weight Loss Prediction: " + weightLoss + " kg");
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Please enter the date in yyyy-MM-dd format.");
            e.printStackTrace();
        }
    }

    private double calculateWeightLossForDateRange(Date startDate, Date endDate) {
        // Implement your weight loss calculation logic here
        // You can use the provided exercise and calorie data to calculate the weight loss
        // For example, 1 kg of fat = 7,700 kcal, so you can calculate weight loss based on the calorie deficit
        // between exercise and calorie intake over the selected date range.

        // This is a placeholder method. You should replace it with your actual calculation.
        return 2.5; // Replace with the actual weight loss calculation
    }

    public static void main(String[] args) {
        new LoseWeightPrediction("Weight Loss Prediction");
    }
}
