package src.main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CombinedChartExample {
    private static DefaultCategoryDataset fullDataset = new DefaultCategoryDataset();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void executeChart() {
        saveData();

        String startDateStr = JOptionPane.showInputDialog("Enter start date (yyyy-MM-dd):");
        String endDateStr = JOptionPane.showInputDialog("Enter end date (yyyy-MM-dd):");

        try {
            visualizeChartForUserInput(startDateStr, endDateStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Please enter the date in yyyy-MM-dd format.");
            e.printStackTrace();
        }
    }

    private static void saveData() {
        // Calorie data
        addData("Calories", "2023-01-01", 2000);
        addData("Calories", "2023-01-02", 1500);
        addData("Calories", "2023-01-03", 1800);
        addData("Calories", "2023-01-04", 2000);
        addData("Calories", "2023-01-05", 1200);
        addData("Calories", "2023-01-06", 1800);

        // Exercise data
        addData("Exercise", "2023-01-01", 300);
        addData("Exercise", "2023-01-02", 450);
        addData("Exercise", "2023-01-03", 600);
        addData("Exercise", "2023-01-04", 200);
        addData("Exercise", "2023-01-05", 150);
        addData("Exercise", "2023-01-06", 300);

        // Snack calories
        Random random = new Random();
        for (int i = 1; i <= 6; i++) {
            int snackCalories = 200 + random.nextInt(201);
            fullDataset.addValue(snackCalories, "Snack Calorie Intake", "2023-01-" + String.format("%02d", i));
        }
    }

    private static void addData(String category, String date, int value) {
        fullDataset.addValue(value, category, date);
    }

    private static void visualizeChartForUserInput(String startDateStr, String endDateStr) throws ParseException {
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);

        DefaultCategoryDataset filteredDataset = filterDatasetForPeriod(fullDataset, startDate, endDate);

        visualizeChartForPeriod(filteredDataset, "Calorie Intake and Exercise Calories Per Day", "Day", "Calories");
    }

    private static DefaultCategoryDataset filterDatasetForPeriod(DefaultCategoryDataset dataset, Date startDate, Date endDate) {
        DefaultCategoryDataset filteredDataset = new DefaultCategoryDataset();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            String currentDateString = dateFormat.format(calendar.getTime());
            for (int i = 0; i < dataset.getRowCount(); i++) {
                String category = (String) dataset.getRowKey(i);
                Number value = dataset.getValue(category, currentDateString);
                if (value != null) {
                    filteredDataset.addValue(value, category, currentDateString);
                }
            }
            calendar.add(Calendar.DATE, 1);
        }

        return filteredDataset;
    }

    private static void visualizeChartForPeriod(DefaultCategoryDataset dataset, String chartTitle, String domainLabel, String rangeLabel) {
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, domainLabel, rangeLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
        customizeChart(chart);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setContentPane(chartPanel);
        frame.setTitle(chartTitle);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void customizeChart(JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();

        // Customize category axis labels
        CategoryAxis categoryAxis = plot.getDomainAxis();
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        // Customize legend placement
        chart.getLegend().setPosition(RectangleEdge.RIGHT);

        // Customize bar width and add line renderer for snack calories
        BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
        barRenderer.setMaximumBarWidth(0.15);

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        plot.setDataset(1, fullDataset);
        plot.setRenderer(1, lineRenderer);
    }
}