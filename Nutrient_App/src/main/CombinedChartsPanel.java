package src.main;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import src.client.Authentication.ChooseProfile;
import src.client.LogData.Dashboard;
import src.client.LogData.DatePanel;
import src.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombinedChartsPanel extends ApplicationFrame {


    static DefaultCategoryDataset  dataset = new DefaultCategoryDataset();
    static DefaultPieDataset dataset1 = new DefaultPieDataset();
    User user ;
    public CombinedChartsPanel(String title, User user) {
        super(title);
        JPanel combinedPanel = createCombinedPanel();
        setContentPane(combinedPanel);
        this.user = user;
        // Create and add button panel here
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


        // Add button panel to the frame
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createCombinedPanel() {
        // Main panel with BoxLayout for vertical stacking
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Date selection panel with horizontal layout
        JPanel dateSelectionPanel = new JPanel();
        dateSelectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Add DatePanel for start and end dates
        DatePanel datePanelStart = new DatePanel();
        datePanelStart.setDate("Start Day (yyyy-mm-dd): ");
        dateSelectionPanel.add(datePanelStart);
        DatePanel datePanelEnd = new DatePanel();
        datePanelEnd.setDate("End Day (yyyy-mm-dd): ");
        dateSelectionPanel.add(datePanelEnd);

        // Add date selection panel to main panel
        mainPanel.add(dateSelectionPanel);
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start Date: " + datePanelStart.getSelectedDate());
                System.out.println("End Date: " + datePanelEnd.getSelectedDate());
            }
        });
        dateSelectionPanel.add(submit);
        // Panel for charts
        JPanel chartPanel = new JPanel(new GridLayout(1, 2)); // Horizontal layout

        // Add the bar/line chart and pie chart
        JFreeChart barLineChart = createBarLineChart();
        ChartPanel barLineChartPanel = new ChartPanel(barLineChart);
        chartPanel.add(barLineChartPanel);
        JFreeChart pieChart = createPieChart();
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        chartPanel.add(pieChartPanel);

        // Add chart panel to main panel
        mainPanel.add(chartPanel);

        // Create and add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton recalculateButton = new JButton("Recalculate");
        JButton profileButton = new JButton("Profile");
        JButton logDietButton = new JButton("Log Diet");
        buttonPanel.add(recalculateButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(logDietButton);

        // Add button panel to main panel
        mainPanel.add(buttonPanel);

        recalculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseProfile(user);
            }
        });

        logDietButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard().callDashBoard();
            }
        });

        return mainPanel;
    }
    private static void addDataforLineChart(String category, String date, int value) {
        dataset.addValue(value, category, date);
    }

    private JFreeChart createBarLineChart() {
        // Add data to the dataset
        dataset.addValue(2000, "Calories", "2023-01-01");
        dataset.addValue(1500, "Calories", "2023-01-02");
        dataset.addValue(1800, "Calories", "2023-01-03");
        dataset.addValue(2000, "Calories", "2023-01-04");
        dataset.addValue(1200, "Calories", "2023-01-05");
        dataset.addValue(1800, "Calories", "2023-01-06");

        // Exercise data
        dataset.addValue(300, "Exercise", "2023-01-01");
        dataset.addValue(450, "Exercise", "2023-01-02");
        dataset.addValue(600, "Exercise", "2023-01-03");
        dataset.addValue(200, "Exercise", "2023-01-04");
        dataset.addValue(150, "Exercise", "2023-01-05");
        dataset.addValue(300, "Exercise", "2023-01-06");

        // Create and return the chart
        return ChartFactory.createBarChart(
                "Bar/Line Chart", "Category", "Value", dataset,
                PlotOrientation.VERTICAL, true, true, false
        );
    }


    private JFreeChart createPieChart() {

        // Example data
        dataset1.setValue("Fruit and Vegetable", 20);
        dataset1.setValue("Starch", 30);
        dataset1.setValue("Milk and Dairy Food", 15);
        dataset1.setValue("Food and Drink High in Fat/Sugar", 15);
        dataset1.setValue("Meat and Fish", 10);
        dataset1.setValue("Other", 30);

        return ChartFactory.createPieChart(
                "Pie Chart", dataset1, true, true, false
        );
    }

    public  void execute() {
        CombinedChartsPanel demo = new CombinedChartsPanel("Combined Charts Example", this.user);
        System.out.println(this.user.getUsername());
        System.out.println(this.user.getPassword());
        System.out.println(this.user.getFirstName());
        System.out.println(this.user.getLastName());
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setVisible(true);
    }
}
