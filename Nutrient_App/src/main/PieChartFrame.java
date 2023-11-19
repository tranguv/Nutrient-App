package src.main;

import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class PieChartFrame extends JFrame {
    public PieChartFrame(String title, JFreeChart chart) {
        ChartPanel panel = new ChartPanel(chart);
        getContentPane().add(panel);
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
