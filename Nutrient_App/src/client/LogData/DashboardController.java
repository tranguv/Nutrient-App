package src.client.LogData;

import javax.swing.*;

import src.model.DateLog;
import src.model.FoodItem;
import src.model.Ingredient;
import src.model.Meal;
import src.model.MealType;
import src.server.DataServices.DateQueries;
import src.server.DataServices.MealQueries;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
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

        // dashboardGUI.getSaveLogButton().addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         saveLog();
        //     }
        // });
    }

    private DateLog addDate(){
        String date = dashboardGUI.getDatePanel().getSelectedDate();

        // Split the date string
        String[] parts = date.split("-");

        // Extract month, day, and year
        String year = parts[0];
        String month = parts[1];
        String day = parts[2];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));

        DateLog selectedDate = new DateLog(2, cal.getTime());
        int dateID = DateQueries.addDate(selectedDate);
        System.out.println("Date ID: " + dateID);
        selectedDate.setDateLogId(dateID);
        return selectedDate;
    }

    private void addMeal() {
        List<Ingredient> ingredientList = new ArrayList<>();
        String mealType = dashboardGUI.getMealPanel().getMealTypeComboBox().getSelectedItem().toString();
        MealType mealTypeEnum = MealType.valueOf(mealType.toUpperCase());

        List<String> ingredients = new ArrayList<>();
        List<String> quantities = new ArrayList<>();
        List<String> units = new ArrayList<>();

        for (Component component : dashboardGUI.getMealPanel().getComponents()) {
            System.out.println("compo:" + component.getName());
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                System.out.println("text:" + text);
                if (!text.isEmpty()) {
                    System.out.println("inside JTextField");
                    if (textField.getName().equals("quantity")) {
                        quantities.add(text);
                    } else if (textField.getName().equals("unit")) {
                        units.add("g");
                    } else if (textField.getName().equals("foodgroup")) {
                        continue;
                    }
                }
            } else if (component instanceof JComboBox) {
                System.out.println("inside JComboBox");
                JComboBox<?> comboBox = (JComboBox<?>) component;
                Object selectedItem = comboBox.getSelectedItem();
                if (selectedItem != null) {
                    System.out.println("selectedItem:" + selectedItem.toString());
                    if (comboBox.getName().equals("unit")) {
                        units.add(selectedItem.toString());
                    } else {
                        System.out.println("ingredient add nha");
                        ingredients.add(selectedItem.toString());
                    }
                }
            }
        }

        ingredients.remove(0);
        System.out.println("ingre:" + ingredients.toString());
        System.out.println("quantities:" + quantities.toString());
        System.out.println("units:" + units.toString());

        for (int i = 0; i < ingredients.size(); i++) {
            FoodItem foodItem = MealQueries.getFoodItem(ingredients.get(i));
            Ingredient ingredient = new Ingredient(foodItem, Integer.parseInt(quantities.get(i)), units.get(i));
            ingredientList.add(ingredient);
        }

        System.out.println("ingreList " + ingredientList.toString());
        DateLog dateLog = addDate();
        
        Meal meal = new Meal(mealTypeEnum);
        meal.addIngredients(ingredientList);
        int mealID = MealQueries.addMeal(dateLog, meal);
        System.out.println("Meal added successfully!");
        for (Ingredient ingredient : ingredientList) {
            System.out.println("ingredient: " + ingredient.toString());
            MealQueries.addIngredients(mealID, ingredient);
        }
        System.out.println("Ingredient added successfully!");
        // Update meals log
        StringBuilder historyEntry = new StringBuilder("Added meal: " + mealType + " - ");

        // if (!ingredients.isEmpty()) {
        //     for (int i = 0; i < ingredients.size(); i++) {
        //         historyEntry.append(ingredients.get(i)).append(": ")
        //                 .append(quantities.get(i)).append(" ").append(units.get(i)).append(", ");
        //     }
        //     historyEntry.setLength(historyEntry.length() - 2); // Remove the last comma and space
        // } else {
        //     historyEntry.append("No ingredients added");
        // }

        // Update history
        // updateHistory(historyEntry.toString());
    }

    // private void updateHistory(String newEntry) {
    //     // Your history update logic goes here
    //     dashboardGUI.getHistoryTextArea().append(newEntry + "\n");
    //     dashboardGUI.getHistoryTextArea().setCaretPosition(dashboardGUI.getHistoryTextArea().getDocument().getLength());
    // }

    private void saveLog() {
        // Your save log logic goes here
        System.out.println("Meals Log:\n" + dashboardGUI.getMealsTextArea().getText());
        System.out.println("Exercises Log:\n" + dashboardGUI.getExercisesTextArea().getText());
    }
}

