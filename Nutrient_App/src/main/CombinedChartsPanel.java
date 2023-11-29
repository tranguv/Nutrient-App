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
import src.model.Exercise;
import src.model.MainApplication;
import src.model.User;
import src.server.DataServices.DailyNutrientIntakeViz;
import src.server.DataServices.ExerciseQueries;
import src.server.DataServices.MealQueries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class CombinedChartsPanel extends ApplicationFrame {
public class CombinedChartsPanel extends ApplicationFrame {
    private DatePanel datePanelStart, datePanelEnd;
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private DefaultPieDataset pieDataset = new DefaultPieDataset();
    private DefaultPieDataset dietAlign = new DefaultPieDataset();

    private User user;
    private MainApplication mainapp = new MainApplication();
    public CombinedChartsPanel(String title) throws ParseException {
        super(title);
        this.user = mainapp.getUser();

        this.datePanelStart = createDatePanel("Start Day (yyyy-mm-dd): ");
        this.datePanelEnd = createDatePanel("End Day (yyyy-mm-dd): ");
        initializeData();
        JPanel combinedPanel = createCombinedPanel();
        setContentPane(combinedPanel);
        setupButtonPanel();
    }

    private void initializeData() throws ParseException {
        if (datePanelStart == null || datePanelEnd == null) {
            // Handle the situation where datePanelStart or datePanelEnd is null
            // You might want to show an error message or set default values
            System.out.println("date panel null");
            return;
        }
        initializeLineChartData();
        initializePieChartData();
        System.out.println("sau pire chart");
        initializeDietAlignData();
        System.out.println("sau diet align");

    }

    private void initializeLineChartData() {
        // Initialize Line Chart Data
        String start = datePanelStart.getSelectedDate();
        System.out.println(start);
        String end = datePanelEnd.getSelectedDate();
        System.out.println(end);

        if (start == null || end == null) {
            // Set default values or handle as needed
            start = "2023-11-15";
            end = "2023-11-22";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(start, formatter);
        LocalDate localDate2 = LocalDate.parse(end, formatter);
        Date startDate = Date.valueOf(localDate);
        Date endDate = Date.valueOf(localDate2);

        ArrayList<Exercise> exercises = ExerciseQueries.getExercisesByDate(user.getId(), startDate, endDate);
        HashMap<String, HashMap<Double, String>> top5 = new HashMap<>();
        for (Exercise e : exercises) {
            if (!top5.containsKey(e.getName())) {
                top5.put(e.getName(), new HashMap<>());
            }
            top5.get(e.getName()).put(e.getCaloriesBurnt(), e.getDate().toString());
        }

        for (String key : top5.keySet()) {
            dataset.addValue(top5.get(key).keySet().iterator().next(), key, top5.get(key).values().iterator().next());
        }

    }

    private void initializePieChartData() throws ParseException {
        // Initialize Pie Chart Data

        String start = datePanelStart.getSelectedDate();
        System.out.println(start);
        String end = datePanelEnd.getSelectedDate();
        System.out.println(end);

        if (start == null || end == null) {
            // Set default values or handle as needed
            start = "2023-11-15";
            end = "2023-11-22";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(start, formatter);
        LocalDate localDate2 = LocalDate.parse(end, formatter);
        Date startDate = Date.valueOf(localDate);
        Date endDate = Date.valueOf(localDate2);

        List<DailyNutrientIntakeViz> daily = DailyNutrientIntakeViz.getNutrientValConsumed(MainApplication.getUser().getId(), startDate, endDate);
        System.out.println(daily);

        List<DailyNutrientIntakeViz> top5Nutrients;
        if (daily.size() >= 5) {
            top5Nutrients = daily.subList(0,5);
            List<DailyNutrientIntakeViz> remain = daily.subList(5, daily.size());
            int totalQuantity = 0;
            double totalNutAmt = 0;
            for (DailyNutrientIntakeViz i : remain) {
                totalQuantity += i.getTotalQuantity();
                totalNutAmt += i.getTotalNutrientAmt();
            }
            DailyNutrientIntakeViz combined = new DailyNutrientIntakeViz(totalQuantity, "Other", totalNutAmt);
            top5Nutrients.add(combined);
        }
        else top5Nutrients = daily.subList(0, daily.size());

        HashMap<String, Double> top5 = new HashMap<>();
        for (DailyNutrientIntakeViz d : top5Nutrients) {
            top5.put(d.getNutrientName(), d.getTotalNutrientAmt());
        }

        for (String key : top5.keySet()) {
            pieDataset.setValue(key, top5.get(key));
        }

    }

    private void initializeDietAlignData() {
        // Initialize Pie Chart Data
        HashMap<String, Double> dietAlignData = MealQueries.userDietaryRestrictionsMet(MainApplication.getUser().getId());
        //replace with food group name
        for (String key : dietAlignData.keySet()) {
            dietAlign.setValue(key, dietAlignData.get(key));
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
        this.datePanelStart = createDatePanel("Start Day (yyyy-mm-dd): ");
        this.datePanelEnd = createDatePanel("End Day (yyyy-mm-dd): ");
        dateSelectionPanel.add(datePanelStart);
        dateSelectionPanel.add(datePanelEnd);

        JButton submitButton = createSubmitButton(datePanelStart, datePanelEnd);
        dateSelectionPanel.add(submitButton);

        return dateSelectionPanel;
    }

    public DatePanel getDatePanelStart() {
        return datePanelStart;
    }

    public DatePanel getDatePanelEnd() {
        return datePanelEnd;
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
                try {
                    initializeData();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                SwingUtilities.invokeLater(() -> {
                    createCombinedPanel().revalidate();
                    createCombinedPanel().repaint();
                });
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
        logDietButton.addActionListener(e -> {
            new Dashboard().callDashBoard();
            dispose();
        });

        return logDietButton;
    }


    private void setupButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void execute() throws ParseException {
        CombinedChartsPanel demo = new CombinedChartsPanel("Combined Charts Example");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        demo.setVisible(true);
    }
}