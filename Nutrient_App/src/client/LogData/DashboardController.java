package src.client.LogData;

import src.server.DataServices.MealQueries;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    private DashboardGUI dashboardGUI;

    public DashboardController(DashboardGUI dashboardGUI) {
        this.dashboardGUI = dashboardGUI;
        initializeListeners();
    }

    private void initializeListeners() {
        dashboardGUI.getAddMealButton().addActionListener(this::addMeal);
        dashboardGUI.getAddExerciseButton().addActionListener(e -> addExercise());
        dashboardGUI.getSaveLogButton().addActionListener(e -> saveLog());
    }

    private void addMeal(ActionEvent e) {
        String mealType = dashboardGUI.getMealPanel().getMealTypeComboBox().getSelectedItem().toString();
        List<String> ingredients = new ArrayList<>();
        List<String> quantities = new ArrayList<>();
        List<String> units = new ArrayList<>();

        processComponents(dashboardGUI.getMealPanel(), ingredients, quantities, units);

//        StringBuilder historyEntry = new StringBuilder("Added meal: " + mealType + " - ");
//        for (int i = 0; i < ingredients.size(); i++) {
//            if (i < quantities.size() && i < units.size()) {
//                historyEntry.append(ingredients.get(i)).append(": ")
//                        .append(quantities.get(i)).append(" ").append(units.get(i)).append(", ");
//            } else {
//                // Handle the case where the lists are not of equal length
//                // For example, log an error or append a default value
//            }
//        }
//
//
//        if (historyEntry.lastIndexOf(", ") > -1) {
//            historyEntry.setLength(historyEntry.length() - 2); // Remove the last comma and space
//        }
//        System.out.println(historyEntry.toString());
//        updateHistory(historyEntry.toString());
    }

    private void processComponents(JPanel panel, List<String> ingredients, List<String> quantities, List<String> units) {
        String mealtypevalue = null;
        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel) {
                // Recursive call for nested JPanel components
                processComponents((JPanel) component, ingredients, quantities, units);
            } else if (component instanceof JTextField) {
                // Process JTextField components
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                if (!text.isEmpty()) {
                    String fieldName = textField.getName();
                    if ("quantity".equals(fieldName)) {
                        quantities.add(text);
                    } else if ("ingredient".equals(fieldName)) {
                        ingredients.add(text);
                    }
                    // Units are not handled here since they are JComboBox
                }
            } else if (component instanceof JComboBox) {
                // Process JComboBox components
                JComboBox<?> comboBox = (JComboBox<?>) component;
                Object selectedItem = comboBox.getSelectedItem();
                if (selectedItem != null) {
                    String fieldName = comboBox.getName();
                    if ("unit".equals(fieldName)) {
                        units.add(selectedItem.toString());
                    } else if ("mealtype".equals(fieldName)) {
                        mealtypevalue = selectedItem.toString();
                    } else {
                        // Default case, assuming other combo boxes are for ingredients
                        ingredients.add(selectedItem.toString());
                    }
                }
            }
            MealQueries a = new MealQueries();
            System.out.println(a.getMealID());;
        }
        // Print out the lists for debugging purposes
        System.out.println("Meal Type: " + mealtypevalue);
        System.out.println("Ingredients: " + listToString(ingredients));
        System.out.println("Quantities: " + listToString(quantities));
        System.out.println("Units: " + listToString(units));
    }

    private String listToString(List<String> list) {
        return String.join(", ", list);
    }




    private void addExercise() {
        // Implement exercise adding logic here
    }

    private void updateHistory(String newEntry) {
        dashboardGUI.getHistoryTextArea().append(newEntry + "\n");
        dashboardGUI.getHistoryTextArea().setCaretPosition(dashboardGUI.getHistoryTextArea().getDocument().getLength());
    }

    private void saveLog() {
        System.out.println("Meals Log:\n" + dashboardGUI.getMealsTextArea().getText());
        System.out.println("Exercises Log:\n" + dashboardGUI.getExercisesTextArea().getText());
    }
}
