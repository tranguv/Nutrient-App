package src.main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartCreator {
    private DefaultPieDataset dataset;

    public PieChartCreator() {
        dataset = new DefaultPieDataset();
    }

    public void addCategory(String name, double value) {
        dataset.setValue("Fruit and Vegetable", 20);
        dataset.setValue("Starch", 30);
        dataset.setValue("Milk and Dairy Food", 15);
        dataset.setValue("Food and Drink High in Fat/Sugar", 15);
        dataset.setValue("Meat and Fish", 10);
        dataset.setValue("Other", 30);

    }

    public JFreeChart createChart(String title) {
        return ChartFactory.createPieChart(
                title,
                dataset,
                true,
                true,
                false
        );
    }
}
