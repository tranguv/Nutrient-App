package src.client.LogData;
import src.client.LogData.DashboardGUI;
import src.client.LogData.DashboardController;

import javax.swing.SwingUtilities;

public class Dashboard {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardGUI dashboardGUI = new DashboardGUI();
            new DashboardController(dashboardGUI);
        });
    }
}

