package src.main;

import org.jfree.data.general.DefaultPieDataset;

/**
 * Class to manage the dataset of a pie chart.
 */
public class PieChartDatasetManager {
    private DefaultPieDataset dataset;

    public PieChartDatasetManager() {
        dataset = new DefaultPieDataset();
    }

    /**
     * Adds a new category to the dataset.
     *
     * @param name  The name of the category.
     * @param value The value associated with the category.
     */
    public void addCategory(String name, double value) {
        dataset.setValue(name, value);
    }

    public DefaultPieDataset getDataset() {
        return dataset;
    }
}
