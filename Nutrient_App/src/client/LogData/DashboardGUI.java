package src.client.LogData;

import src.main.CombinedChartsPanel;
import src.model.MainApplication;
import src.model.User;
import src.server.DataServices.MealQueries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardGUI extends JFrame {
    private DatePanel datePanel;
    private MealPanel mealPanel;
    private ExercisePanel exercisePanel;
    private JButton addMealButton, addExerciseButton, saveLogButton;
    private JTextArea mealsTextArea, exercisesTextArea, historyTextArea;
    private JButton returnToMainButton;
    private MealQueries meal;
    private User user;
    public DashboardGUI() {
        setTitle("Nutrition App");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        this.user = new MainApplication().getUser();
        // Initialize panels
        this.datePanel = new DatePanel();
        this.mealPanel = new MealPanel();
        this.exercisePanel = new ExercisePanel();
        this.meal = new MealQueries();
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
        returnToMainButton = new JButton("Return to Main Page");
        returnToMainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to return to main page
                // This might involve closing the current window and opening the main page window
                // For example:
                DashboardGUI.this.dispose();

                // Open the CombinedChartsPanel (main page)
                // Assuming the CombinedChartsPanel constructor sets up the window correctly
                CombinedChartsPanel mainPage = new CombinedChartsPanel("User");
                mainPage.execute();
                mainPage.setVisible(true);

            }
        });

        // Add the new button to the buttonPanel or another appropriate panel
        buttonPanel.add(returnToMainButton);
        if (meal.userHasRecords(this.user.getId())){
            returnToMainButton.setVisible(true);

        }else{
            returnToMainButton.setVisible(false);
        }
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    // Add getters for accessing panels and buttons if needed
    private void checkUserRecords() {
        if (meal.userHasRecords(this.user.getId())) {
            returnToMainButton.setVisible(true);
        } else {
            returnToMainButton.setVisible(false);
        }
    }

    public DatePanel getDatePanel() {
        return datePanel;
    }

    public MealPanel getMealPanel() {
        return mealPanel;
    }
    public JButton getReturnToMainButton() {
        return returnToMainButton;
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