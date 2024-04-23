import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class UserTest {
  @Test
  public void testUser() {
    List<String> friends = Arrays.asList("friend1", "friend2");
    User user = new User("username", "John Doe", "CA", friends);

    assertEquals("username", user.getUsername());
    assertEquals("John Doe", user.getDisplayName());
    assertEquals("CA", user.getState());
    assertEquals(friends, user.getFriends());
  }
}