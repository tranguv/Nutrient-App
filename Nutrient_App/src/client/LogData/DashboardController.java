package src.client.LogData;

import javax.swing.*;

import src.model.*;
import src.server.DataServices.DateQueries;
import src.server.DataServices.ExerciseQueries;
import src.server.DataServices.MealQueries;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DashboardController {
    private DashboardGUI dashboardGUI;
    private MainApplication main = new MainApplication();
    private User user;
    public DashboardController(DashboardGUI dashboardGUI) {
        this.dashboardGUI = dashboardGUI;
        this.user = main.getUser();
        initializeListeners();
    }

    private void initializeListeners() {
        dashboardGUI.getAddMealButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMeal();
                System.out.println("Log Meal Succesfully");
            }
        });

        dashboardGUI.getAddExerciseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add exercise logic if needed
                addExercise();
                System.out.println("Log Excersices Succesfully");
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

        if(date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")){
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Please select a valid date (YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        // Split the date string
        String[] parts = date.split("-");

        // Extract year, month, and day
        String year = parts[0];
        String month = parts[1];
        String day = parts[2];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        System.out.println(this.user.getId());
        DateLog selectedDate = new DateLog(this.user.getId(), cal.getTime());
        int dateID = DateQueries.addDate(selectedDate);
        selectedDate.setDateLogId(dateID);
        return selectedDate;
    }

    private void addMeal() {
        MealPanel mealPanel = dashboardGUI.getMealPanel();

        List<Ingredient> ingredientList = new ArrayList<>();
        String mealType = dashboardGUI.getMealPanel().getMealTypeComboBox().getSelectedItem().toString();
        MealType mealTypeEnum = MealType.valueOf(mealType.toUpperCase());

        List<String> ingredients = new ArrayList<>();
        List<String> quantities = new ArrayList<>();
        List<String> units = new ArrayList<>();

        GridBagLayout layout = (GridBagLayout) mealPanel.getLayout();

        for (Component component : mealPanel.getComponents()) {
            GridBagConstraints gbc = layout.getConstraints(component);

            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                if (!text.isEmpty()) {
                    if (textField.getName().equals("quantity")) {
                        quantities.add(text);
                    } else if (textField.getName().equals("unit")) {
                        units.add("g");
                    } else if (textField.getName().equals("foodgroup")) {
                        continue;
                    }
                }
            } else if (component instanceof JComboBox) {
                JComboBox<?> comboBox = (JComboBox<?>) component;
                Object selectedItem = comboBox.getSelectedItem();
                if (selectedItem != null) {
                    if (comboBox.getName().equals("unit")) {
                        units.add(selectedItem.toString());
                    } else {
                        ingredients.add(selectedItem.toString());
                    }
                }
            }
        }

        ingredients.remove(0);

        for (int i = 0; i < ingredients.size(); i++) {
            FoodItem foodItem = MealQueries.getFoodItem(ingredients.get(i));
            Ingredient ingredient = new Ingredient(foodItem, Integer.parseInt(quantities.get(i)), units.get(i));
            ingredientList.add(ingredient);
        }

        DateLog dateLog = addDate();

        Meal meal = new Meal(mealTypeEnum);
        meal.addIngredients(ingredientList);
        Date date = dateLog.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        if(!(mealType.equals("Snack")) && MealQueries.checkMealType(strDate, mealTypeEnum.toString())){
            System.out.println("mealtype" + mealType);
            System.out.println(MealQueries.checkMealType(strDate, mealTypeEnum.toString()));
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Meal type already exists for this date", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int mealID = MealQueries.addMeal(dateLog, meal);
        meal.setMealId(mealID);
        for (Ingredient ingredient : ingredientList) {
            MealQueries.addIngredients(mealID, ingredient);
        }
        JOptionPane.showMessageDialog(null, "Meal added successfully!");
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

    private void addExercise() {
        // Your add exercise logic goes here
        //{exerciseName, [duration, intensity]}
        ExercisePanel exercisePanel = dashboardGUI.getExercisePanel();
        List<Exercise> exerciseList = new ArrayList<>();
        List<String> exercises = new ArrayList<>();
        List<Integer> duration = new ArrayList<>();
        List<String> intensity = new ArrayList<>();

        GridBagLayout layout = (GridBagLayout) exercisePanel.getLayout();

        // Iterate over the components of the ExercisePanel
        for (Component component : exercisePanel.getComponents()) {
            System.out.println("compo:" + component.getName());
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                System.out.println("text:" + text);
                if (!text.isEmpty()) {
                    System.out.println("inside JTextField");
                    if (textField.getName().equals("Duration")) {
                        duration.add(Integer.parseInt(text));
                    } else if (textField.getName().equals("OtherExercise") && !text.equals("Manually input your activity")) {
                        exercises.add(text);
                    }
                }
            } else if (component instanceof JComboBox) {
                System.out.println("inside JComboBox");
                JComboBox<?> comboBox = (JComboBox<?>) component;
                Object selectedItem = comboBox.getSelectedItem();
                if (selectedItem != null) {
                    System.out.println("selectedItem:" + selectedItem.toString());
                    if (comboBox.getName().equals("Exercise")) {
                        if (selectedItem.toString().equals("Other")) {
                            continue;
                        } else {
                            exercises.add(selectedItem.toString());
                        }
                    } else {
                        System.out.println("intensity add nha");
                        intensity.add(selectedItem.toString());
                    }
                }
            }
        }

        // exercises.remove(-1);
        // Move this block outside of the loop
        System.out.println("ingre:" + exercises.toString());
        System.out.println("quantities:" + duration.toString());
        System.out.println("units:" + intensity.toString());
        for (int i = 0; i < exercises.size(); i++) {
            Intensity intensityEnum = Intensity.valueOf(intensity.get(i).toLowerCase());
            Exercise exercise = new Exercise(exercises.get(i), duration.get(i), intensityEnum);
            exerciseList.add(exercise);
        }
    
        System.out.println("ingreList " + exerciseList.toString());
        DateLog dateLog = addDate();
    
        if (exerciseList.size() > 0) {
            int exerciseID = ExerciseQueries.logExercise(dateLog, exerciseList.get(0));
            exerciseList.get(0).setID(exerciseID);
            JOptionPane.showMessageDialog(null, "Keep up the good work!");
        } else {
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Please enter an exercise", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
