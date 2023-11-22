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
import src.model.MainApplication;
import src.model.User;
import src.server.DataServices.MealQueries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

public class CombinedChartsPanel extends ApplicationFrame {

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private DefaultPieDataset pieDataset = new DefaultPieDataset();
    private DefaultPieDataset dietAlign = new DefaultPieDataset();

    private User user;
    private MainApplication mainapp = new MainApplication();
    public CombinedChartsPanel(String title) {
        super(title);
        this.user = mainapp.getUser();

        initializeData();
        JPanel combinedPanel = createCombinedPanel();
        setContentPane(combinedPanel);
        setupButtonPanel();
    }

    private void initializeData() {
        initializeLineChartData();
        initializePieChartData();
        initializeDietAlignData();
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

    private void initializeDietAlignData() {
        // Initialize Pie Chart Data
        Random rand = new Random();
        HashMap<String, String> dietAlignData = MealQueries.userDietaryRestrictionsMet(MainApplication.getUser().getId());
        //replace with food group name
        for (String key : dietAlignData.keySet()) {
            dietAlign.setValue(key, Integer.parseInt(dietAlignData.get(key)));
        }
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
        JPanel chartPanel = new JPanel(new GridLayout(2, 2));

        Dimension chartSize = new Dimension(800, 500);
        chartPanel.add(createChartPanel(createBarLineChart(), chartSize));
        chartPanel.add(createChartPanel(createPieChart(), chartSize));
        chartPanel.add(createChartPanel(createDietAlignPanel(), chartSize));
        chartPanel.add(createWeightPredictionPanel(chartSize));

        return chartPanel;
    }

    private JPanel createWeightPredictionPanel(Dimension size) {
        WeightPredictionPanel weightPredictionPanel = new WeightPredictionPanel();
        weightPredictionPanel.setPreferredSize(size);

        // Center the WeightPredictionPanel within its parent container
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.add(weightPredictionPanel);

        return centeredPanel;
    }


    private ChartPanel createChartPanel(JFreeChart chart, Dimension size) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(size);
        return chartPanel;
    }

    private JFreeChart createDietAlignPanel() {
        return ChartFactory.createPieChart(
                        "Diet Align with Canadian Food Guide", dietAlign, true, true, false);
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
        MainApplication a = new MainApplication();
        this.user = a.getUser();
        profileButton.addActionListener(e -> new ChooseProfile(this.user));
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
        CombinedChartsPanel demo = new CombinedChartsPanel("Combined Charts Example");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        demo.setVisible(true);
    }
}