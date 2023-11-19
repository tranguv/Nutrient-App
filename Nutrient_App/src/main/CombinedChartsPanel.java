package src.main;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import src.client.LogData.Dashboard;
import src.client.LogData.DatePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombinedChartsPanel extends ApplicationFrame {




    public CombinedChartsPanel(String title) {
        super(title);
        JPanel combinedPanel = createCombinedPanel();
        setContentPane(combinedPanel);

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


    private JFreeChart createBarLineChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Example data
        dataset.addValue(1.0, "Series1", "Category1");
        dataset.addValue(4.0, "Series1", "Category2");
        dataset.addValue(3.0, "Series2", "Category1");
        dataset.addValue(5.0, "Series2", "Category2");

        return ChartFactory.createBarChart(
                "Bar/Line Chart", "Category", "Value", dataset,
                PlotOrientation.VERTICAL, true, true, false
        );
    }

    private JFreeChart createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        // Example data
        dataset.setValue("Category1", 43.2);
        dataset.setValue("Category2", 10.0);
        dataset.setValue("Category3", 27.5);
        dataset.setValue("Category4", 17.5);

        return ChartFactory.createPieChart(
                "Pie Chart", dataset, true, true, false
        );
    }

    public  void execute() {
        CombinedChartsPanel demo = new CombinedChartsPanel("Combined Charts Example");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setVisible(true);
    }
}
