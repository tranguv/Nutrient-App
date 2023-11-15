package src.client;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;


public class Dashboard extends JFrame{

    private JTextField mealTypeField, ingredientNameField, quantityField, unitField,
            exerciseTypeField, durationField, intensityField ;
    private JComboBox<String> mealTypeComboBox, ingredientCombo;
    private JTextArea mealsTextArea, exercisesTextArea;
    private JButton addMealButton, addExerciseButton, saveLogButton;
    private JTextArea historyTextArea;

    public Dashboard() {

        setTitle("Nutrifit App");
        setSize(1000, 600); // Increased width for larger text fields
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Add spacing between components

        JPanel inputPanel = new JPanel(new GridBagLayout());

        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding between components

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Meal Type:"), gbc);
        String[] mealTypes = {"Breakfast", "Lunch", "Dinner", "Snack"};
        mealTypeComboBox = new JComboBox<>(mealTypes);
        mealTypeComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        inputPanel.add(mealTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Ingredient Name:"), gbc);
        String[] ingredients = {
                "Dairy and Egg Products",
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
                "Snacks"
        };
        ingredientCombo = new JComboBox<>(ingredients);
        gbc.gridx = 1;

        ingredientCombo.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        inputPanel.add(ingredientCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        inputPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Unit:"), gbc);
        gbc.gridx = 1;
        unitField = new JTextField();
        unitField.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        inputPanel.add(unitField, gbc);

        gbc.gridx = 2; // Move to the next column
        gbc.gridy = 0; // Reset the row
        gbc.fill = GridBagConstraints.NONE; // Reset the fill property

        inputPanel.add(new JLabel("Exercise Type:"), gbc);
        gbc.gridx = 3;
        exerciseTypeField = new JTextField();
        exerciseTypeField.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        inputPanel.add(exerciseTypeField, gbc);

        gbc.gridx = 2;
        gbc.gridy++;
        inputPanel.add(new JLabel("Duration:"), gbc);
        gbc.gridx = 3;
        durationField = new JTextField();
        durationField.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        inputPanel.add(durationField, gbc);

        gbc.gridx = 2;
        gbc.gridy++;
        inputPanel.add(new JLabel("Intensity:"), gbc);
        gbc.gridx = 3;
        intensityField = new JTextField();
        intensityField.setPreferredSize(new Dimension(200, 30)); // Set preferred size
        inputPanel.add(intensityField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addMealButton = new JButton("Add Meal");
        addExerciseButton = new JButton("Add Exercise");
        buttonPanel.add(addMealButton);
        buttonPanel.add(addExerciseButton);

        JPanel logPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns (variable columns)
        logPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mealsTextArea = new JTextArea();
        exercisesTextArea = new JTextArea();
        logPanel.add(new JScrollPane(mealsTextArea));
        logPanel.add(new JScrollPane(exercisesTextArea));

        JPanel savePanel = new JPanel(new FlowLayout());
        saveLogButton = new JButton("Save Log");
        savePanel.add(saveLogButton);

        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false); // Make it read-only
        JScrollPane historyScrollPane = new JScrollPane(historyTextArea); // Add scroll pane
        historyScrollPane.setPreferredSize(new Dimension(980, 100)); // Set preferred size

        // Adjust layout
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);
        historyPanel.setBorder(BorderFactory.createTitledBorder("History"));
        add(historyPanel, BorderLayout.AFTER_LAST_LINE);


        JPanel southPanel = new JPanel(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.SOUTH);
        add(savePanel, BorderLayout.SOUTH);

        addMealButton.addActionListener(e -> addMeal());
        addExerciseButton.addActionListener(e -> addExercise());
        saveLogButton.addActionListener(e -> saveLog());

        setVisible(true);
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
        String selectedMealType = (String) mealTypeComboBox.getSelectedItem();
        String selectedIngredient = (String) ingredientCombo.getSelectedItem();
        String mealInfo = String.format("Meal Type: %s, Ingredients: %s, Quantity: %s, Unit: %s",
                selectedMealType, selectedIngredient,
                quantityField.getText(), unitField.getText());
        mealsTextArea.append(mealInfo + "\n");
        updateHistory("Added Meal: " + mealInfo);
        clearMealFields();
    }

    private void clearMealFields() {
        // Removed the mealTypeField line as it's no longer in use
        ingredientNameField.setText("");
        quantityField.setText("");
        unitField.setText("");
    }

    private void addExercise() {
        String exerciseInfo = String.format("Exercise Type : %s, Duration: %s minutes, Intensity: %s",
                exerciseTypeField.getText(), durationField.getText(), intensityField.getText());
        exercisesTextArea.append(exerciseInfo + "\n");
        updateHistory("Added Exercise : " + exerciseInfo);
        clearExerciseFields();
    }


    private void saveLog() {
        System.out.println("Meals Log:\n" + mealsTextArea.getText());
        System.out.println("Exercises Log:\n" + exercisesTextArea.getText());
    }

    private void clearExerciseFields() {
        mealTypeField.setText("");
        exerciseTypeField.setText("");
        durationField.setText("");
        intensityField.setText("");
    }

    public static void main(String[] args) {
        Dashboard d = new Dashboard();
    }
	
}
