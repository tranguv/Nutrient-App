package src.client.LogData;

import javax.swing.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    private DashboardGUI dashboardGUI;

    public DashboardController(DashboardGUI dashboardGUI) {
        this.dashboardGUI = dashboardGUI;
        initializeListeners();
    }

    private void initializeListeners() {
        dashboardGUI.getAddMealButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMeal();
            }
        });

        dashboardGUI.getAddExerciseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add exercise logic if needed
            }
        });

        dashboardGUI.getSaveLogButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLog();
            }
        });
    }

    private void addMeal() {
        String mealType = dashboardGUI.getMealPanel().getMealTypeComboBox().getSelectedItem().toString();

        List<String> ingredients = new ArrayList<>();
        List<String> quantities = new ArrayList<>();
        List<String> units = new ArrayList<>();

        for (Component component : dashboardGUI.getMealPanel().getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                if (!text.isEmpty()) {
                    if (textField.getName().equals("ingredient")) {
                        ingredients.add(text);
                    } else if (textField.getName().equals("quantity")) {
                        quantities.add(text);
                    } else if (textField.getName().equals("unit")) {
                        units.add(text);
                    }
                }
            } else if (component instanceof JComboBox) {
                JComboBox<?> comboBox = (JComboBox<?>) component;
                Object selectedItem = comboBox.getSelectedItem();
                if (selectedItem != null) {
                    ingredients.add(selectedItem.toString());
                }
            }
        }

        StringBuilder historyEntry = new StringBuilder("Added meal: " + mealType + " - ");

        if (!ingredients.isEmpty()) {
            for (int i = 0; i < ingredients.size(); i++) {
                historyEntry.append(ingredients.get(i)).append(": ")
                        .append(quantities.get(i)).append(" ").append(units.get(i)).append(", ");
            }
            historyEntry.setLength(historyEntry.length() - 2); // Remove the last comma and space
        } else {
            historyEntry.append("No ingredients added");
        }

        // Update history
        updateHistory(historyEntry.toString());
    }

    private void updateHistory(String newEntry) {
        // Your history update logic goes here
        dashboardGUI.getHistoryTextArea().append(newEntry + "\n");
        dashboardGUI.getHistoryTextArea().setCaretPosition(dashboardGUI.getHistoryTextArea().getDocument().getLength());
    }

    private void saveLog() {
        // Your save log logic goes here
        System.out.println("Meals Log:\n" + dashboardGUI.getMealsTextArea().getText());
        System.out.println("Exercises Log:\n" + dashboardGUI.getExercisesTextArea().getText());
    }
}

