package src.test;

import src.model.User;
import src.server.DataServices.UserQueries;

public class UserTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            User newUser = new User("test5", "test1", "ok", "nha", "F", "2003-11-11", 60, 170, "metric");
            UserQueries.createUser(newUser);
            System.out.println("id: " + newUser.getId()); // Debugging statement
            System.out.println("User signed up successfully!");
        } catch (NumberFormatException ex) {
            System.out.println("number");
        } catch (Exception ex) {
            System.out.println("other");        
        }
    }
}
