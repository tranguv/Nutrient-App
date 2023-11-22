import org.junit.jupiter.api.Test;
import src.model.User;

public class UserTest {
    /* Purpose of this test is to test sign up functionality*/
    @Test
    public void test_user_1(){
        /* create new user */
        User user1 = new User("annavu", "123456", "Anna", "Vu", "F", "2003-02-22", 55, 162, "metric");

    }
}
