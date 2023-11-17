package src.client;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;  // Make sure to import this at the beginning of your file
import java.util.List;
import  src.client.S12FocusLost;

import javax.swing.*;


public class Dashboard extends JFrame {

    private JComboBox<String> mealTypeComboBox, ingredientCombo, intensityCombo, unitCombo;
    private JTextField quantityField, exerciseTypeField, durationField;
    private JTextArea mealsTextArea, exercisesTextArea, historyTextArea;
    private JButton addMealButton, addExerciseButton, saveLogButton;
    private JPanel mealPanel;
    private S12FocusLost autoSuggest;
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

        JComboBox comboBox = new JComboBox(new Object[] {"Cheese souffle","Chop suey, with meat, canned","Chinese dish, chow mein, chicken","Corn fritter","Beef pot roast, with browned potatoes, peas and corn","Fried chicken, mashed potatoes and vegetables","Meat loaf with tomato sauce, mashed potatoes and peas","Sliced turkey with mashed potatoes and peas","Vinegar, cider","Vinegar, distilled (white)","Butter, whipped","Butter oil, anhydrous","Cheese, blue","Cheese, brick","Cheese, brie","Cheese, camembert","Cheese, caraway","Cheese, cheshire","Cheese, colby","Cheese, cottage, creamed (4.5% M.F.)","Cheese, cottage, creamed with fruit","Cheese, cottage, uncreamed, dry curd (0.4% M.F.)","Cheese, cream","Cheese, edam","Cheese, fontina","Cheese, gjetost","Cheese, gruyere"
                ,"Cheese, limburger","Cheese, monterey","Cheese, mozzarella, (48% water, 25% M.F.)","Cheese, muenster","Cheese, neufchatel","Cheese, parmesan, dry grated","Cheese, parmesan, hard","Cheese, port-salut","Cheese, provolone","Cheese, ricotta, with whole milk","Cheese, ricotta, with partly skimmed milk","Cheese, romano","Cheese, roquefort (blue)","Cheese, swiss (emmental)","Cheese, tilsit, with whole milk","Cheese, processed, cheddar, cold pack","Cheese, processed food, swiss, loaf","Cream, whipped, cream topping, pressurized","Eggnog, 7% M.F., Canadian product, 4% to 8% M.F.","Coffee whitener (non dairy), liquid","Dessert topping (non dairy), powdered","Dessert topping (non dairy), powdered, prepared with whole milk","Dessert topping (non dairy), pressurized","Dessert topping (non dairy), frozen, semisolid","Milk, fluid, partly skimmed, 2% M.F.","Milk, fluid, partly skimmed, 1% M.F.","Milk, dry whole","Milk, dry, buttermilk, sweet cream","Milk, condensed, sweetened, canned","Milk, fluid, chocolate, whole","Milk, fluid, chocolate, partly skimmed, 2% M.F.","Hot chocolate, cocoa, homemade, prepared with 2% milk","Milk, fluid, goat, enriched, whole","Milk, fluid, human (breast milk), mature"
                ,"Milk, fluid, sheep, whole","Milk shake, chocolate, thick","Milk shake, vanilla, thick","Whey, acid, fluid","Whey, acid, dry","Whey, sweet, fluid","Whey, sweet, dry","Egg, chicken, dried, whole","Egg, chicken, dried, whole, stabilized","Egg, chicken, white, pan dried, flakes","Egg, chicken, white, dried, powder, glucose reduced","Egg, chicken, yolk, dried","Egg, duck, whole, fresh, raw","Egg, goose, whole, fresh, raw","Egg, quail, whole, fresh, raw","Egg, turkey, whole, fresh, raw","Butter, unsalted","Cheese, parmesan, shredded","Cheese, goat, hard, (less than 35% water, 36% M.F.)","Cheese, goat, semi-soft, (35% to 55% water, 30% M.F.)","Cheese, goat, soft, (more than 55% water, 21% M.F.)","Cheese fondue","Cheese sauce, homemade","Cheese, Mexican, queso anejo","Cheese, Mexican, queso asadero","Cheese, Mexican, queso chihuahua","Cheese, cheddar or colby type, low fat (7% M.F.)","Cheese, cheddar or colby type, low sodium","Cheese, cottage, (1% M.F.)","Cheese, feta","Cheese, gouda","Cheese, mozzarella, (52% water, 22.5% M.F.)","Cheese, mozzarella, partially skim, (52% water, 16.5% M.F.)","Milk, evaporated, skim, canned, undiluted, 0.2% M.F.","Milk, fluid, whole, pasteurized, homogenized, 3.25% M.F.","Milk, fluid, skim","Milk, dry, skim, powder, instant","Hot chocolate, cocoa, homemade, prepared with whole milk","Egg substitute, frozen (yolk replaced)"
                ,"Butter, regular","Cheese, cheddar","Cheese, cottage, (2% M.F.)","Coffee whitener (non dairy), powdered","Milk, fluid, whole, producer, 3.7% M.F.","Milk, fluid, buttermilk, cultured, 1% M.F.","Egg, chicken, whole, fresh or frozen, raw","Egg, chicken, white, fresh or frozen, raw","Egg, chicken, yolk, fresh or frozen, raw","Egg, chicken, whole, cooked, fried","Egg, chicken, whole, cooked, boiled in shell, hard-cooked"
                ,"Egg, chicken, whole, cooked, poached","Egg, chicken, whole, cooked, scrambled or omelet","Milk, dry, skim, powder, regular","Cream, table (coffee), 18% M.F.","Cream, whipping, 32% M.F.","Cream, whipping, 35% M.F.","Cream, sour, cultured, 14% M.F","Milk, evaporated, whole, canned, undiluted, 7.8% M.F.","Cheese, cottage, uncreamed, dry, large or small curd, nonfat","Cream, cereal (half and half), 10% M.F.","Cream, table (coffee), 15% M.F.","Cream, sour, cultured, 18% M.F.","Milk, evaporated, partly skimmed, canned, undiluted, 2% M.F.","Cheese, calorie-reduced (6% M.F.)","Cream, table (coffee), 20% M.F.","Dessert, frozen yogourt","Spices, allspice, ground","Spices, anise seeds","Spices, basil, dried","Spices, bay leaf","Spices, caraway seed","Spices, cardamom, ground","Spices, celery seed","Spices, chervil, dried","Spices, chili powder","Spices, cinnamon, ground","Spices, cloves, ground","Spices, coriander leaf (cilantro), dried","Spices, coriander seed (cilantro)","Spices, cumin seed","Spices, curry powder","Spices, dill seed","Spices, dill weed, dried","Spices, fennel seed","Spices, fenugreek seed","Spices, garlic powder","Spices, ginger, ground","Spices, mace, ground","Spices, marjoram, dried","Spices, mustard seed, yellow","Spices, nutmeg, ground","Spices, onion powder","Spices, oregano, ground","Spices, paprika",
        });

        autoSuggest = new S12FocusLost(comboBox);
        ingredientCombo = comboBox;
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
        unitCombo = new JComboBox<>(new String[] {"kg", "lb"});
        unitCombo.setPreferredSize(new Dimension(100, 30)); // Adjusted size to provide space
        panel.add(unitCombo, gbc);
        mealTypeComboBox.setName("mealtype");
        ingredientCombo.setName("ingredient");
        quantityField.setName("quantity");
        unitCombo.setName("unit");

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
        JComboBox newIngredientCombo = new JComboBox(new Object[] {"Cheese souffle","Chop suey, with meat, canned","Chinese dish, chow mein, chicken","Corn fritter","Beef pot roast, with browned potatoes, peas and corn","Fried chicken, mashed potatoes and vegetables","Meat loaf with tomato sauce, mashed potatoes and peas","Sliced turkey with mashed potatoes and peas","Vinegar, cider","Vinegar, distilled (white)","Butter, whipped","Butter oil, anhydrous","Cheese, blue","Cheese, brick","Cheese, brie","Cheese, camembert","Cheese, caraway","Cheese, cheshire","Cheese, colby","Cheese, cottage, creamed (4.5% M.F.)","Cheese, cottage, creamed with fruit","Cheese, cottage, uncreamed, dry curd (0.4% M.F.)","Cheese, cream","Cheese, edam","Cheese, fontina","Cheese, gjetost","Cheese, gruyere"
                ,"Cheese, limburger","Cheese, monterey","Cheese, mozzarella, (48% water, 25% M.F.)","Cheese, muenster","Cheese, neufchatel","Cheese, parmesan, dry grated","Cheese, parmesan, hard","Cheese, port-salut","Cheese, provolone","Cheese, ricotta, with whole milk","Cheese, ricotta, with partly skimmed milk","Cheese, romano","Cheese, roquefort (blue)","Cheese, swiss (emmental)","Cheese, tilsit, with whole milk","Cheese, processed, cheddar, cold pack","Cheese, processed food, swiss, loaf","Cream, whipped, cream topping, pressurized","Eggnog, 7% M.F., Canadian product, 4% to 8% M.F.","Coffee whitener (non dairy), liquid","Dessert topping (non dairy), powdered","Dessert topping (non dairy), powdered, prepared with whole milk","Dessert topping (non dairy), pressurized","Dessert topping (non dairy), frozen, semisolid","Milk, fluid, partly skimmed, 2% M.F.","Milk, fluid, partly skimmed, 1% M.F.","Milk, dry whole","Milk, dry, buttermilk, sweet cream","Milk, condensed, sweetened, canned","Milk, fluid, chocolate, whole","Milk, fluid, chocolate, partly skimmed, 2% M.F.","Hot chocolate, cocoa, homemade, prepared with 2% milk","Milk, fluid, goat, enriched, whole","Milk, fluid, human (breast milk), mature"
                ,"Milk, fluid, sheep, whole","Milk shake, chocolate, thick","Milk shake, vanilla, thick","Whey, acid, fluid","Whey, acid, dry","Whey, sweet, fluid","Whey, sweet, dry","Egg, chicken, dried, whole","Egg, chicken, dried, whole, stabilized","Egg, chicken, white, pan dried, flakes","Egg, chicken, white, dried, powder, glucose reduced","Egg, chicken, yolk, dried","Egg, duck, whole, fresh, raw","Egg, goose, whole, fresh, raw","Egg, quail, whole, fresh, raw","Egg, turkey, whole, fresh, raw","Butter, unsalted","Cheese, parmesan, shredded","Cheese, goat, hard, (less than 35% water, 36% M.F.)","Cheese, goat, semi-soft, (35% to 55% water, 30% M.F.)","Cheese, goat, soft, (more than 55% water, 21% M.F.)","Cheese fondue","Cheese sauce, homemade","Cheese, Mexican, queso anejo","Cheese, Mexican, queso asadero","Cheese, Mexican, queso chihuahua","Cheese, cheddar or colby type, low fat (7% M.F.)","Cheese, cheddar or colby type, low sodium","Cheese, cottage, (1% M.F.)","Cheese, feta","Cheese, gouda","Cheese, mozzarella, (52% water, 22.5% M.F.)","Cheese, mozzarella, partially skim, (52% water, 16.5% M.F.)","Milk, evaporated, skim, canned, undiluted, 0.2% M.F.","Milk, fluid, whole, pasteurized, homogenized, 3.25% M.F.","Milk, fluid, skim","Milk, dry, skim, powder, instant","Hot chocolate, cocoa, homemade, prepared with whole milk","Egg substitute, frozen (yolk replaced)"
                ,"Butter, regular","Cheese, cheddar","Cheese, cottage, (2% M.F.)","Coffee whitener (non dairy), powdered","Milk, fluid, whole, producer, 3.7% M.F.","Milk, fluid, buttermilk, cultured, 1% M.F.","Egg, chicken, whole, fresh or frozen, raw","Egg, chicken, white, fresh or frozen, raw","Egg, chicken, yolk, fresh or frozen, raw","Egg, chicken, whole, cooked, fried","Egg, chicken, whole, cooked, boiled in shell, hard-cooked"
                ,"Egg, chicken, whole, cooked, poached","Egg, chicken, whole, cooked, scrambled or omelet","Milk, dry, skim, powder, regular","Cream, table (coffee), 18% M.F.","Cream, whipping, 32% M.F.","Cream, whipping, 35% M.F.","Cream, sour, cultured, 14% M.F","Milk, evaporated, whole, canned, undiluted, 7.8% M.F.","Cheese, cottage, uncreamed, dry, large or small curd, nonfat","Cream, cereal (half and half), 10% M.F.","Cream, table (coffee), 15% M.F.","Cream, sour, cultured, 18% M.F.","Milk, evaporated, partly skimmed, canned, undiluted, 2% M.F.","Cheese, calorie-reduced (6% M.F.)","Cream, table (coffee), 20% M.F.","Dessert, frozen yogourt","Spices, allspice, ground","Spices, anise seeds","Spices, basil, dried","Spices, bay leaf","Spices, caraway seed","Spices, cardamom, ground","Spices, celery seed","Spices, chervil, dried","Spices, chili powder","Spices, cinnamon, ground","Spices, cloves, ground","Spices, coriander leaf (cilantro), dried","Spices, coriander seed (cilantro)","Spices, cumin seed","Spices, curry powder","Spices, dill seed","Spices, dill weed, dried","Spices, fennel seed","Spices, fenugreek seed","Spices, garlic powder","Spices, ginger, ground","Spices, mace, ground","Spices, marjoram, dried","Spices, mustard seed, yellow","Spices, nutmeg, ground","Spices, onion powder","Spices, oregano, ground","Spices, paprika",
        });
        // Ingredient ComboBox
        gbc.gridx++;
        new S12FocusLost(newIngredientCombo);
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
        gbc.gridx++;
        unitCombo = new JComboBox(new  String[] {"kg", "lb"});
        unitCombo.setPreferredSize(new Dimension(100, 30)); // Adjusted size to provide space
        panel.add(unitCombo, gbc);

        // Unit Field

        newIngredientCombo.setName("ingredient");
        newQuantityField.setName("quantity");
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
        intensityCombo = new JComboBox(new Object[] {"Low", "Medium", "High"});
        gbc.gridx = 1;
        panel.add(intensityCombo, gbc);
        intensityCombo.setPreferredSize(new Dimension(100, 30));
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