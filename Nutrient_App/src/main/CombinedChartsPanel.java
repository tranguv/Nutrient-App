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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CombinedChartsPanel extends ApplicationFrame {

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private DefaultPieDataset pieDataset = new DefaultPieDataset();
    private User user;
    private MainApplication mainapp = new MainApplication();
    String start ;
    String end;
    public CombinedChartsPanel(String title)  {
        super(title);
        this.user = mainapp.getUser();

        initializeData();
        JPanel combinedPanel = createCombinedPanel();
        setContentPane(combinedPanel);
        setupButtonPanel();
    }

    private void initializeData()  {
        initializeLineChartData();
        initializePieChartData();
    }
    private void initializeDataV2(){
        initializeLineChartDataV2();
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
            System.out.println("Start Date: " +start.getSelectedDate() );
            System.out.println("End Date: " + end.getSelectedDate());
            this.start =start.getSelectedDate();
            this.end = end.getSelectedDate();
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
        recalculationButton.addActionListener(e -> {
            try {
                initializeDataV2();
                getContentPane().removeAll(); // Remove all components
                getContentPane().add(createCombinedPanel()); // Add updated panel
                getContentPane().revalidate();
                getContentPane().repaint();
            } catch (Exception ex) {
                ex.printStackTrace(); // Print any exception for debugging
            }
        });
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
        logDietButton.addActionListener(e -> {
            new Dashboard().callDashBoard();
            dispose(); // Dispose the frame when button is clicked
        });
        return logDietButton;
    }

    private void setupButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void execute()  {
        CombinedChartsPanel demo = new CombinedChartsPanel("Combined Charts Example");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        demo.setVisible(true);
    }
    private void initializeLineChartDataV2() {
        int userID = this.user.getId(); // Assuming there's a method to get the user ID
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startFix = null;
        Date endFix = null;

        try {
            startFix = dateFormat.parse(this.start);
            endFix = dateFormat.parse(this.end);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return; // Exit the method if parsing fails
        }

        // Clear previous data
        dataset.clear();

        Calendar start = Calendar.getInstance();
        start.setTime(startFix);
        Calendar end = Calendar.getInstance();
        end.setTime(endFix);

        for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            ArrayList<Double> dailyKcalIntakes = new MealQueries().getDailyKcalIntake(userID, new java.sql.Date(date.getTime()));

            // Assuming you want to sum up all kcal for a day
            double dailyTotal = dailyKcalIntakes.stream().mapToDouble(Double::doubleValue).sum();

            dataset.addValue(dailyTotal, "Calories", new SimpleDateFormat("yyyy-MM-dd").format(date));
        }
    }


}