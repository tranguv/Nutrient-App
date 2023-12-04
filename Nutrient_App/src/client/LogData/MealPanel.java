package src.client.LogData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.List;

import src.server.DataServices.MealQueries;

public class MealPanel extends JPanel {
    private JComboBox<String> mealTypeComboBox, ingredientCombo, unitCombo;
    private JTextField quantityField;
    private S12FocusLost autoSuggest;
    private int ingredientRowCount = 1;
    private List<String> foodItems = MealQueries.getFoodDescription();

    public MealPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Log Meal"));
        addMealFields();
    }

//    private void addMealFields() {
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Meal Type Label
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        add(new JLabel("Meal Type:"), gbc);
//
//        // Meal Type ComboBox
//        gbc.gridx++;
//        mealTypeComboBox = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner", "Snack"});
//        mealTypeComboBox.setPreferredSize(new Dimension(100, 30));
//        add(mealTypeComboBox, gbc);
//
//        // Ingredient Name Label
//        gbc.gridx++;
//        add(new JLabel("Ingredient Name:"), gbc);
//
//        // Ingredient ComboBox
//        gbc.gridx++;
//        JComboBox comboBox = new JComboBox(foodItems.toArray());
//        autoSuggest = new S12FocusLost(comboBox);
//        ingredientCombo = comboBox;
//        ingredientCombo.setPreferredSize(new Dimension(200, 30));
//        add(ingredientCombo, gbc);
//
//        // Food Group Label
//        gbc.gridx++;
//        add(new JLabel("Food Group:"), gbc);
//
//        // Food Group Display Field
//        gbc.gridx++;
//        JTextField foodGroup = new JTextField();
//        foodGroup.setPreferredSize(new Dimension(200, 30));
//        foodGroup.setEditable(false);
//        ingredientCombo.addActionListener(e -> {
//            String selectedIngredient = ingredientCombo.getSelectedItem().toString();
//            String foodGroupName = MealQueries.getFoodGroupName(selectedIngredient);
//            foodGroup.setText(foodGroupName);
//            updateUnitCombo(foodGroup.getText());
//        });
//        add(foodGroup, gbc);
//
//        // Quantity Label
//        gbc.gridx++;
//        add(new JLabel("Quantity:"), gbc);
//
//        // Quantity Field
//        gbc.gridx++;
//        quantityField = new JTextField();
//        quantityField.setPreferredSize(new Dimension(100, 30));
//        add(quantityField, gbc);
//
//        // Unit Label
//        gbc.gridx++;
//        add(new JLabel("Unit:"), gbc);
//
//        // Unit Field
//        gbc.gridx++;
//        String units[] = {"g", "ml", "cup"};
//        unitCombo = new JComboBox<>(units);
//        unitCombo.setPreferredSize(new Dimension(100, 30));
//        add(unitCombo, gbc);
//
//        mealTypeComboBox.setName("mealtype");
//        ingredientCombo.setName("ingredient");
//        quantityField.setName("quantity");
//        unitCombo.setName("unit");
//        foodGroup.setName("foodgroup");
//
//        // Add Ingredient Button
//        JButton addIngredientButton = new JButton("+");
//        addIngredientButton.setPreferredSize(new Dimension(50, 30));
//        gbc.gridx++;
//        gbc.gridwidth = 1;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        add(addIngredientButton, gbc);
//
//        addIngredientButton.addActionListener(e -> {
//            addIngredientFields();
//            revalidate();
//            repaint();
//        });
//    }
//
//
private void addMealFields() {
    GridBagConstraints gbc = initializeGridBagConstraints();

    addMealTypeComponents(gbc);
    addIngredientComponents(gbc);
    addFoodGroupComponents(gbc);
    addQuantityComponents(gbc);
    addUnitComponents(gbc);
    addIngredientButton(gbc);
}

    private GridBagConstraints initializeGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addMealTypeComponents(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Meal Type:"), gbc);

        gbc.gridx++;
        mealTypeComboBox = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner", "Snack"});
        mealTypeComboBox.setPreferredSize(new Dimension(100, 30));
        add(mealTypeComboBox, gbc);
    }

    private void addIngredientComponents(GridBagConstraints gbc) {
        gbc.gridx++;
        add(new JLabel("Ingredient Name:"), gbc);

        gbc.gridx++;
        JComboBox comboBox = new JComboBox(foodItems.toArray());
        autoSuggest = new S12FocusLost(comboBox);
        ingredientCombo = comboBox;
        ingredientCombo.setPreferredSize(new Dimension(200, 30));
        add(ingredientCombo, gbc);
    }

    private void addFoodGroupComponents(GridBagConstraints gbc) {
        gbc.gridx++;
        add(new JLabel("Food Group:"), gbc);

        gbc.gridx++;
        JTextField foodGroup = new JTextField();
        foodGroup.setPreferredSize(new Dimension(200, 30));
        foodGroup.setEditable(false);
        add(foodGroup, gbc);

        setupFoodGroupListener(foodGroup);
    }

    private void setupFoodGroupListener(JTextField foodGroup) {
        ingredientCombo.addActionListener(e -> {
            String selectedIngredient = ingredientCombo.getSelectedItem().toString();
            String foodGroupName = MealQueries.getFoodGroupName(selectedIngredient);
            foodGroup.setText(foodGroupName);
            updateUnitCombo(foodGroup.getText());
        });
    }

    private void addQuantityComponents(GridBagConstraints gbc) {
        gbc.gridx++;
        add(new JLabel("Quantity:"), gbc);

        gbc.gridx++;
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(100, 30));
        add(quantityField, gbc);
    }

    private void addUnitComponents(GridBagConstraints gbc) {
        gbc.gridx++;
        add(new JLabel("Unit:"), gbc);

        gbc.gridx++;
        String units[] = {"g", "ml", "cup"};
        unitCombo = new JComboBox<>(units);
        unitCombo.setPreferredSize(new Dimension(100, 30));
        add(unitCombo, gbc);
    }

    private void addIngredientButton(GridBagConstraints gbc) {
        JButton addIngredientButton = new JButton("+");
        addIngredientButton.setPreferredSize(new Dimension(50, 30));
        gbc.gridx++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(addIngredientButton, gbc);

        addIngredientButton.addActionListener(e -> {
            addIngredientFields();
            revalidate();
            repaint();
        });
    }


    private void updateUnitCombo(String foodGroupName) {
        String[] units;

        int foodGroupIndex = MealQueries.getFoodGroupID(foodGroupName);
        switch (foodGroupIndex) {
            default:
                units = new String[]{"g", "ml"}; // Default units
                break;
            case 1: //dairy n egg
                units = new String[]{"ml", "cup", "egg"};
                break;
            case 5:
                units = new String[]{"g"};
                break;
            case 8:
            case 10:
            case 13:
            case 15:
            case 17:
            case 20:
                units = new String[]{"g", "ml"};
                break;
            case 9: //fruits
                units = new String[]{"fruit", "ml"};
                break;
            case 4: //fats n oils
            case 12: //nuts n seed
            case 16: //legumes
            case 11: // vegetables
                units = new String[]{"ml", "g"};
                break;
            case 14: //beverages
                units = new String[]{"ml", "cup"};
                break;
            case 22:
                units = new String[]{"dish"};
                break;
            case 2:
            case 3:
            case 6:
            case 7:
            case 18:
            case 19:
            case 21:
                units = new String[]{"g", "ml", "cup"};
                break;
        }

        unitCombo.setModel(new DefaultComboBoxModel<>(units));
    }

    private void addIngredientFields() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = ingredientRowCount;
        int nextRow = getComponentCount() / 8;

        // Ingredient Name Label
        gbc.gridx = 2;
        add(new JLabel("Ingredient Name:"), gbc);
        JComboBox newIngredientCombo = new JComboBox(foodItems.toArray());

        // Ingredient ComboBox
        gbc.gridx++;
        new S12FocusLost(newIngredientCombo);
        newIngredientCombo.setPreferredSize(new Dimension(200, 30));
        add(newIngredientCombo, gbc);

        // Food Group Label
        gbc.gridx++;
        add(new JLabel("Food Group:"), gbc);

        // Food Group Display Field
        gbc.gridx++;
        JTextField foodGroup = new JTextField();
        foodGroup.setPreferredSize(new Dimension(200, 30));
        foodGroup.setEditable(false);
        newIngredientCombo.addActionListener(e -> {
            String selectedIngredient = newIngredientCombo.getSelectedItem().toString();
            String foodGroupName = MealQueries.getFoodGroupName(selectedIngredient);
            foodGroup.setText(foodGroupName);
            updateUnitCombo(foodGroup.getText());
        });
        add(foodGroup, gbc);

        // Quantity Label
        gbc.gridx++;
        add(new JLabel("Quantity:"), gbc);

        // Quantity Field
        gbc.gridx++;
        JTextField newQuantityField = new JTextField();
        newQuantityField.setPreferredSize(new Dimension(100, 30));
        add(newQuantityField, gbc);

        // Unit Label
        gbc.gridx++;
        add(new JLabel("Unit:"), gbc);
        gbc.gridx++;
        unitCombo = new JComboBox(new String[]{"g", "lb"});
        unitCombo.setPreferredSize(new Dimension(100, 30));
        add(unitCombo, gbc);

        newIngredientCombo.setName("ingredient");
        foodGroup.setName("foodgroup");
        newQuantityField.setName("quantity");
        unitCombo.setName("unit");
        ingredientRowCount++;
    }

    public JComboBox<String> getMealTypeComboBox() {
        return mealTypeComboBox;
    }

    public JComboBox<String> getIngredientCombo() {
        return ingredientCombo;
    }

    public JComboBox<String> getUnitCombo() {
        return unitCombo;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }
}


