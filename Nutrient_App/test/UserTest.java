//import org.junit.jupiter.api.Test;
//import src.model.User;
//import src.server.DataServices.UserQueries;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.sql.SQLException;
//import java.sql.SQLIntegrityConstraintViolationException;
//
//public class UserTest {
//    /* Purpose of this test is to test sign up functionality*/
//    @Test
//    void test_user_1() throws SQLException {
//        /* create new user */
//        User user1 = new User("annavu", "123456", "Anna", "Vu", "F", "2003-02-22", 55, 162, "metric");
//        UserQueries.createUser(user1);
//        int userID = UserQueries.getUserIDbyUsername(user1.getUsername());
//        user1.setId(userID);
//
//        User expected = UserQueries.getUserByID(userID);
//        assertEquals(user1.getUsername(), expected.getUsername());
//        assertEquals(user1, expected);
//    }
//
//    @Test
//    void name() {
//    }
//}
