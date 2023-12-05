package src.client.LogData;
import src.client.LogData.DashboardGUI;
import src.client.LogData.DashboardController;

import javax.swing.SwingUtilities;
import java.sql.SQLException;

public class Dashboard {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardGUI dashboardGUI = null;
            dashboardGUI = new DashboardGUI();
            new DashboardController(dashboardGUI);
        });
    }
    public void callDashBoard(){
        SwingUtilities.invokeLater(() -> {
            DashboardGUI dashboardGUI = null;
            dashboardGUI = new DashboardGUI();
            new DashboardController(dashboardGUI);
        });
    }
}

