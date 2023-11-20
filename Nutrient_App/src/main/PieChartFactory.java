package src.main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Factory for creating pie charts.
 */
public class PieChartFactory {

    /**
     * Creates a pie chart with the given title and dataset.
     *
     * @param title The title of the chart.
     * @param dataset The dataset for the chart.
     * @return A JFreeChart object representing the pie chart.
     */
    public static JFreeChart createPieChart(String title, DefaultPieDataset dataset) {
        return ChartFactory.createPieChart(title, dataset, true, true, false);
    }
}

