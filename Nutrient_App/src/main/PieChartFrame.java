package src.main;

import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 * Frame for displaying a pie chart.
 */
public class PieChartFrame extends JFrame {

    public PieChartFrame(String title, JFreeChart chart) {
        super(title);
        initializeChartPanel(chart);
    }

    private void initializeChartPanel(JFreeChart chart) {
        ChartPanel panel = new ChartPanel(chart);
        getContentPane().add(panel);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
