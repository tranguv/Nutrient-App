package src.client.LogData;

import javax.swing.SwingUtilities;

public class Dashboard {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardGUI dashboardGUI = new DashboardGUI();
            new DashboardController(dashboardGUI);
        });
    }
    public void callDashBoard(){
        SwingUtilities.invokeLater(() -> {
            DashboardGUI dashboardGUI = new DashboardGUI();
            new DashboardController(dashboardGUI);
        });
    }
}

