package src.test;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JOptionPane;

import src.client.Authentication.SignUpPage;
import src.model.User;
import src.server.DataServices.MealQueries;
import src.server.DataServices.UserQueries;

public class MealTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            User newUser = new User("test3", "test1", "ok", "nha", "F", "2003-11-11", 60, 170, "metric");
					
            // Move the createUser method call into the try block
            if (UserQueries.createUser(newUser)) {
                newUser.setId(UserQueries.getUserID());
                System.out.println("id trong test: " + newUser.getId()); // Debugging statement
                System.out.println("User signed up successfully!");
                MealQueries.addMeal(newUser, "breakfast");
                System.out.println("Meal added successfully!");
            } else {
                System.out.println("User signed up unsuccessfully!");
            }
        } catch (NumberFormatException ex) {
            System.out.println("number");
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("SQL");
        } catch (Exception ex) {
            System.out.println("other");        
        }
    }
}
