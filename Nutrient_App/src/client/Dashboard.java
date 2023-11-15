package src.client;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;  // Make sure to import this at the beginning of your file
import java.util.List;


import javax.swing.*;


public class Dashboard extends JFrame {

    private JComboBox<String> mealTypeComboBox, ingredientCombo;
    private JTextField quantityField, unitField, exerciseTypeField, durationField, intensityField;
    private JTextArea mealsTextArea, exercisesTextArea, historyTextArea;
    private JButton addMealButton, addExerciseButton, saveLogButton;
    private JPanel mealPanel;
    private int ingredientRowCount = 1;

    public Dashboard() {
        setTitle("Nutrition App");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create the panel for meal inputs
        mealPanel = new JPanel(new GridBagLayout());
        mealPanel.setBorder(BorderFactory.createTitledBorder("Meal Details"));
        addMealFields(mealPanel);

        // Create the panel for exercise inputs
        JPanel exercisePanel = new JPanel(new GridBagLayout());
        exercisePanel.setBorder(BorderFactory.createTitledBorder("Exercise Details"));
        addExerciseFields(exercisePanel);

        // Combine meal and exercise panels
        JPanel combinedInputPanel = new JPanel();
        combinedInputPanel.setLayout(new BoxLayout(combinedInputPanel, BoxLayout.Y_AXIS));
        combinedInputPanel.add(mealPanel);
        combinedInputPanel.add(exercisePanel);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addMealButton = new JButton("Add Meal");
        addExerciseButton = new JButton("Add Exercise");
        buttonPanel.add(addMealButton);
        buttonPanel.add(addExerciseButton);

        // Log panel
        JPanel logPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        logPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mealsTextArea = new JTextArea();
        exercisesTextArea = new JTextArea();
        logPanel.add(new JScrollPane(mealsTextArea));
        logPanel.add(new JScrollPane(exercisesTextArea));

        // Save button panel
        JPanel savePanel = new JPanel(new FlowLayout());
        saveLogButton = new JButton("Save Log");
        savePanel.add(saveLogButton);

        // History text area
        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        JScrollPane historyScrollPane = new JScrollPane(historyTextArea);
        historyScrollPane.setPreferredSize(new Dimension(980, 100));

        // History panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);
        historyPanel.setBorder(BorderFactory.createTitledBorder("History"));

        // Add panels to the frame
        add(combinedInputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.SOUTH);
        add(savePanel, BorderLayout.AFTER_LAST_LINE);
        add(historyPanel, BorderLayout.AFTER_LAST_LINE);

        // Add action listeners
        addMealButton.addActionListener(e -> addMeal());
//        addExerciseButton.addActionListener(e -> addExercise());
        saveLogButton.addActionListener(e -> saveLog());

        setVisible(true);
    }

    private void addMealFields(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Meal Type Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Meal Type:"), gbc);

        // Meal Type ComboBox
        gbc.gridx++;
        mealTypeComboBox = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner", "Snack"});
        mealTypeComboBox.setPreferredSize(new Dimension(200, 30));
        panel.add(mealTypeComboBox, gbc);


        // Ingredient Name Label
        gbc.gridx++;
        panel.add(new JLabel("Ingredient Name:"), gbc);

        // Ingredient ComboBox
        gbc.gridx++;
        ingredientCombo = new JComboBox<>(new String[]{"Dairy and Egg Products",
                "Spices and Herbs",
                "Babyfoods",
                "Fats and Oils",
                "Poultry Products",
                "Soups, Sauces and Gravies",
                "Sausages and Luncheon Meats",
                "Breakfast Cereals",
                "Fruits and Fruit Juices",
                "Pork Products",
                "Vegetables and Vegetable Products",
                "Nuts and Seeds",
                "Beef Products",
                "Beverages",
                "Finfish and Shellfish Products",
                "Legumes and Legume Products",
                "Lamb, Veal, and Game",
                "Baked Products",
                "Sweets",
                "Cereals, Grains, and Pasta",
                "Fast Foods",
                "Mixed Dishes",
                "Snacks"});
        ingredientCombo.setPreferredSize(new Dimension(200, 30));
        panel.add(ingredientCombo, gbc);

        // Quantity Label
        gbc.gridx++;
        panel.add(new JLabel("Quantity:"), gbc);

        // Quantity Field
        gbc.gridx++;
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(100, 30)); // Adjusted size to provide space
        panel.add(quantityField, gbc);

        // Unit Label
        gbc.gridx++;
        panel.add(new JLabel("Unit:"), gbc);

        // Unit Field
        gbc.gridx++;
        unitField = new JTextField();
        unitField.setPreferredSize(new Dimension(100, 30)); // Adjusted size to provide space
        panel.add(unitField, gbc);
        mealTypeComboBox.setName("mealtype");
        ingredientCombo.setName("ingredient");
        quantityField.setName("quantity");
        unitField.setName("unit");

        // Add Ingredient Button
        JButton addIngredientButton = new JButton("+");
        addIngredientButton.setPreferredSize(new Dimension(50, 30));
        gbc.gridx++; // Move to the next column
        gbc.gridwidth = 1; // Reset grid width if previously altered
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ensure the button fills its grid cell horizontally
        panel.add(addIngredientButton, gbc);
        // Action listener for "Add Ingredient" button
        addIngredientButton.addActionListener(e -> {
            addIngredientFields(panel);
            panel.revalidate();
            panel.repaint();
        });
    }

    private void addIngredientFields(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = ingredientRowCount;    // Use the ingredientRowCount to determine the next row
        int nextRow = panel.getComponentCount() / 8; // Assuming 8 components per ingredient row

        // Ingredient Name Label
        gbc.gridx = 2;

        panel.add(new JLabel("Ingredient Name:"), gbc);

        // Ingredient ComboBox
        gbc.gridx++;
        JComboBox<String> newIngredientCombo = new JComboBox<>(new String[]{"Dairy and Egg Products",
                "Spices and Herbs",
                "Babyfoods",
                "Fats and Oils",
                "Poultry Products",
                "Soups, Sauces and Gravies",
                "Sausages and Luncheon Meats",
                "Breakfast Cereals",
                "Fruits and Fruit Juices",
                "Pork Products",
                "Vegetables and Vegetable Products",
                "Nuts and Seeds",
                "Beef Products",
                "Beverages",
                "Finfish and Shellfish Products",
                "Legumes and Legume Products",
                "Lamb, Veal, and Game",
                "Baked Products",
                "Sweets",
                "Cereals, Grains, and Pasta",
                "Fast Foods",
                "Mixed Dishes",
                "Snacks"});
        newIngredientCombo.setPreferredSize(new Dimension(200, 30));

        panel.add(newIngredientCombo, gbc);

        // Quantity Label
        gbc.gridx++;
        panel.add(new JLabel("Quantity:"), gbc);

        // Quantity Field
        gbc.gridx++;
        JTextField newQuantityField = new JTextField();
        newQuantityField.setPreferredSize(new Dimension(100, 30));

        panel.add(newQuantityField, gbc);

        // Unit Label
        gbc.gridx++;
        panel.add(new JLabel("Unit:"), gbc);

        // Unit Field
        gbc.gridx++;
        JTextField newUnitField = new JTextField();
        newUnitField.setPreferredSize(new Dimension(100, 30));
        panel.add(newUnitField, gbc);

        newIngredientCombo.setName("ingredient");
        newQuantityField.setName("quantity");
        newUnitField.setName("unit");
        ingredientRowCount++;

    }

    private void addExerciseFields(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Exercise Type
        panel.add(new JLabel("Exercise Type:"), gbc);
        exerciseTypeField = new JTextField();
        gbc.gridx = 1;
        panel.add(exerciseTypeField, gbc);
        exerciseTypeField.setPreferredSize(new Dimension(200, 30));
        // Duration
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Duration:"), gbc);
        durationField = new JTextField();
        gbc.gridx = 1;
        panel.add(durationField, gbc);
        durationField.setPreferredSize(new Dimension(200, 30));

        // Intensity
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Intensity:"), gbc);
        intensityField = new JTextField();
        gbc.gridx = 1;
        panel.add(intensityField, gbc);
        intensityField.setPreferredSize(new Dimension(200, 30));
    }

    private void updateHistory(String newEntry) {
        // Format the current date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        // Append the date and new entry followed by a newline to the history text area
        historyTextArea.append(formattedDate + " : " + newEntry + "\n");

        // Scroll the history text area to the bottom
        historyTextArea.setCaretPosition(historyTextArea.getDocument().getLength());
    }

    private void addMeal() {
        // Get the selected meal type
        String mealType = mealTypeComboBox.getSelectedItem().toString();

        // Arrays to store ingredients, quantities, and units
        List<String> ingredients = new ArrayList<>();
        List<String> quantities = new ArrayList<>();
        List<String> units = new ArrayList<>();

        // Iterate over the components in the mealPanel
        for (Component component : mealPanel.getComponents()) {
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
            } else if (component instanceof JComboBox ) {
                JComboBox<?> comboBox = (JComboBox<?>) component;
                Object selectedItem = comboBox.getSelectedItem();
                if (selectedItem != null) {
                    ingredients.add(selectedItem.toString());
                }
            }
        }

        // Concatenate ingredients, quantities, and units for history
        StringBuilder historyEntry = new StringBuilder("Added meal: " + mealType + " - ");
//        for (int i = 0; i < ingredients.size(); i++) {
//            historyEntry.append(ingredients.get(i)).append(": ").append(quantities.get(i)).append(" ").append(units.get(i)).append(", ");
//        }

        // Remove the last comma and space
        if (historyEntry.length() > 0) {
            historyEntry.setLength(historyEntry.length() - 2);
        }
        System.out.println(ingredients.toString());
        System.out.println(ingredients.size());
        System.out.println(units.toString());
        // Update history
        updateHistory(historyEntry.toString());
    }

    private void clearMealFields() {
        // Removed the mealTypeField line as it's no longer in use

        quantityField.setText("");
        unitField.setText("");
    }
//    private void addExercise() {
//        String exerciseInfo = String.format("Exercise Type : %s, Duration: %s minutes, Intensity: %s",
//                exerciseTypeField.getText(), durationField.getText(), intensityField.getText());
//        exercisesTextArea.append(exerciseInfo + "\n");
//        updateHistory("Added Exercise : " + exerciseInfo);
//        clearExerciseFields();
//    }


    private void saveLog() {
        System.out.println("Meals Log:\n" + mealsTextArea.getText());
        System.out.println("Exercises Log:\n" + exercisesTextArea.getText());
    }





    public static void main(String[] args) {
        Dashboard d = new Dashboard();
    }

}