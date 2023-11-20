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
import java.util.Random;

public class CombinedChartsPanel extends ApplicationFrame {

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private DefaultPieDataset pieDataset = new DefaultPieDataset();
    private User user;

    public CombinedChartsPanel(String title, User user) {
        super(title);
        this.user = user;

        initializeData();
        JPanel combinedPanel = createCombinedPanel();
        setContentPane(combinedPanel);
        setupButtonPanel();
    }

    private void initializeData() {
        initializeLineChartData();
        initializePieChartData();
    }

    private void initializeLineChartData() {
        // Initialize Line Chart Data
        Random rand = new Random();
        dataset.addValue(rand.nextInt(2000), "Calories", "2023-01-01");
        dataset.addValue(rand.nextInt(2000), "Calories", "2023-01-02");
        dataset.addValue(rand.nextInt(2000), "Calories", "2023-01-03");
        dataset.addValue(rand.nextInt(2000), "Calories", "2023-01-04");
        dataset.addValue(rand.nextInt(2000), "Calories", "2023-01-05");
        

        // Exercise data
        dataset.addValue(rand.nextInt(500), "Exercise", "2023-01-01");
        dataset.addValue(rand.nextInt(500), "Exercise", "2023-01-02");
        dataset.addValue(rand.nextInt(500), "Exercise", "2023-01-03");
        dataset.addValue(rand.nextInt(500), "Exercise", "2023-01-04");
        dataset.addValue(rand.nextInt(500), "Exercise", "2023-01-05");
        
    }

    private void initializePieChartData() {
        // Initialize Pie Chart Data
        Random rand = new Random();
        pieDataset.setValue("Fruit and Vegetable", rand.nextInt(40));
        pieDataset.setValue("Starch", rand.nextInt(40));
        pieDataset.setValue("Milk and Dairy Food", rand.nextInt(40));
        pieDataset.setValue("Food and Drink High in Fat/Sugar", rand.nextInt(40));
        pieDataset.setValue("Meat and Fish", rand.nextInt(40));
        pieDataset.setValue("Other", rand.nextInt(40));
    }

    private JPanel createCombinedPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(createDateSelectionPanel());
        mainPanel.add(createChartPanel());
        mainPanel.add(createButtonPanel());

        return mainPanel;
    }

    private JPanel createDateSelectionPanel() {
        JPanel dateSelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        DatePanel datePanelStart = createDatePanel("Start Day (yyyy-mm-dd): ");
        DatePanel datePanelEnd = createDatePanel("End Day (yyyy-mm-dd): ");
        dateSelectionPanel.add(datePanelStart);
        dateSelectionPanel.add(datePanelEnd);

        JButton submitButton = createSubmitButton(datePanelStart, datePanelEnd);
        dateSelectionPanel.add(submitButton);

        return dateSelectionPanel;
    }

    private DatePanel createDatePanel(String labelText) {
        DatePanel datePanel = new DatePanel();
        datePanel.setDate(labelText);
        return datePanel;
    }

    private JButton createSubmitButton(DatePanel start, DatePanel end) {
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            System.out.println("Start Date: " + start.getSelectedDate());
            System.out.println("End Date: " + end.getSelectedDate());
        });
        return submit;
    }

    private JPanel createChartPanel() {
        JPanel chartPanel = new JPanel(new GridLayout(1, 2));

        Dimension chartSize = new Dimension(800, 500);
        chartPanel.add(createChartPanel(createBarLineChart(), chartSize));
        chartPanel.add(createChartPanel(createPieChart(), chartSize));

        return chartPanel;
    }

    private ChartPanel createChartPanel(JFreeChart chart, Dimension size) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(size);
        return chartPanel;
    }

    private JFreeChart createBarLineChart() {
        return ChartFactory.createBarChart(
                "Bar/Line Chart", "Category", "Value", dataset,
                PlotOrientation.VERTICAL, true, true, false);
    }

    private JFreeChart createPieChart() {
        return ChartFactory.createPieChart(
                "Pie Chart", pieDataset, true, true, false);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createRecalculateButton());
        buttonPanel.add(createProfileButton());
        buttonPanel.add(createLogDietButton());
        return buttonPanel;
    }
    private JButton createRecalculateButton(){
        JButton recalculationButton = new JButton("Recalculate");
        recalculationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeData();
                createCombinedPanel().revalidate();
                createCombinedPanel().repaint();
            }
        } );
        return recalculationButton;
    }
    private JButton createProfileButton() {
        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(e -> new ChooseProfile(user));
        return profileButton;
    }

    private JButton createLogDietButton() {
        JButton logDietButton = new JButton("Log Diet");
        logDietButton.addActionListener(e -> new Dashboard().callDashBoard());
        return logDietButton;
    }

    private void setupButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void execute() {
        CombinedChartsPanel demo = new CombinedChartsPanel("Combined Charts Example", this.user);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        demo.setVisible(true);
    }
}
