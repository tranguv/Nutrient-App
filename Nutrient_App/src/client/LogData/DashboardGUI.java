package src.client.LogData;

import javax.swing.*;
import java.awt.*;

public class DashboardGUI extends JFrame {
    private DatePanel datePanel;
    private MealPanel mealPanel;
    private ExercisePanel exercisePanel;
    private JButton addMealButton, addExerciseButton, saveLogButton;
    private JTextArea mealsTextArea, exercisesTextArea, historyTextArea;

    public DashboardGUI() {
        setTitle("Nutrition App");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Initialize panels
        this.datePanel = new DatePanel();
        this.mealPanel = new MealPanel();
        this.exercisePanel = new ExercisePanel();

        // Create the combined input panel
        JPanel combinedInputPanel = new JPanel();
        combinedInputPanel.setLayout(new BoxLayout(combinedInputPanel, BoxLayout.Y_AXIS));
        combinedInputPanel.add(datePanel);
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

        // // History text area
        // historyTextArea = new JTextArea();
        // historyTextArea.setEditable(false);
        // JScrollPane historyScrollPane = new JScrollPane(historyTextArea);
        // historyScrollPane.setPreferredSize(new Dimension(980, 100));

        // // History panel
        // JPanel historyPanel = new JPanel(new BorderLayout());
        // historyPanel.add(historyScrollPane, BorderLayout.CENTER);
        // historyPanel.setBorder(BorderFactory.createTitledBorder("History"));

        // Inside the DashboardGUI constructor
        HistoryPanel historyPanel = new HistoryPanel();
        add(historyPanel, BorderLayout.AFTER_LAST_LINE);

        // Inside the DashboardController where you want to update the history
        historyPanel.appendToHistory("New history entry");


        // Add panels to the frame
        add(combinedInputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.SOUTH);
        add(historyPanel, BorderLayout.AFTER_LAST_LINE);

        // Set default close operation and make the frame visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Add getters for accessing panels and buttons if needed
    public DatePanel getDatePanel() {
        return datePanel;
    }

    public MealPanel getMealPanel() {
        return mealPanel;
    }

    public ExercisePanel getExercisePanel() {
        return exercisePanel;
    }

    public JButton getAddMealButton() {
        return addMealButton;
    }

    public JButton getAddExerciseButton() {
        return addExerciseButton;
    }

    public JButton getSaveLogButton() {
        return saveLogButton;
    }

    public JTextArea getMealsTextArea() {
        return mealsTextArea;
    }

    public JTextArea getExercisesTextArea() {
        return exercisesTextArea;
    }

    public JTextArea getHistoryTextArea() {
        return historyTextArea;
    }
}