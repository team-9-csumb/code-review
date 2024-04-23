import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SocialMediaAppTest {

  private SocialMediaApp app;
  private ByteArrayOutputStream outputStream;
  private static final String USER_INFO_FILE = "/Users/tyrellbaker/IdeaProjects/code-review/Main"
      + "/resources/user-info.txt";
  private static final String POST_INFO_FILE = "/Users/tyrellbaker/IdeaProjects/code-review/Main"
      + "/resources/post-info.txt";

  @BeforeEach
  public void setUp() {
    app = new SocialMediaApp();
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  public void testLoadData() {
    app.loadData(USER_INFO_FILE, POST_INFO_FILE);
    assertNotNull(app.users);
    assertNotNull(app.posts);
    assertEquals(20, app.users.size());
    assertEquals(20, app.posts.size());
  }

  @Test
  public void testCheckVisibilityPublicPost() {
    app.loadData(USER_INFO_FILE, POST_INFO_FILE);
    assertTrue(app.checkVisibility("post3", "goldenlover1"));
    assertTrue(app.checkVisibility("post3", "whiskerwatcher"));
  }

  @Test
  public void testCheckVisibilityFriendPost() {
    app.loadData(USER_INFO_FILE, POST_INFO_FILE);
    assertTrue(app.checkVisibility("post1", "petpal4ever"));
    assertTrue(app.checkVisibility("post1", "whiskerwatcher"));
    assertFalse(app.checkVisibility("post1", "kittylover45"));
  }

  @Test
  public void testCheckVisibilityInvalidPost() {
    app.loadData(USER_INFO_FILE, POST_INFO_FILE);
    assertFalse(app.checkVisibility("invalidPost", "goldenlover1"));
  }

  @Test
  public void testRetrievePosts() {
    app.loadData(USER_INFO_FILE, POST_INFO_FILE);
    List<String> visiblePosts = app.retrievePosts("whiskerwatcher");
    assertEquals(Arrays.asList("post1", "post3"), visiblePosts);
  }

  @Test
  public void testSearchUsersByLocation() {
    app.loadData(USER_INFO_FILE, POST_INFO_FILE);
    List<String> matchingUsers = app.searchUsersByLocation("CA");
    assertEquals(List.of("Jane Doe"), matchingUsers);
  }

  @Test
  public void testMenuChoiceLoadData() {
    String input = "1\n" + USER_INFO_FILE + "\n" + POST_INFO_FILE + "\n5\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    SocialMediaApp.main(new String[]{});

    String output = outputStream.toString();
    assertTrue(output.contains("Data loaded successfully."));
  }

  @Test
  public void testMenuChoiceCheckVisibility() {
    String input =
        "1\n" + USER_INFO_FILE + "\n" + POST_INFO_FILE + "\n2\npost2123\npetpal4ever\n5\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    SocialMediaApp.main(new String[]{});

    String output = outputStream.toString();
    assertTrue(output.contains("Access Denied"));
  }

  @Test
  public void testMenuChoiceRetrievePosts() {
    String input = "1\n" + USER_INFO_FILE + "\n" + POST_INFO_FILE + "\n3\nwhiskerwatcher\n5\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    SocialMediaApp.main(new String[]{});

    String output = outputStream.toString();
    assertTrue(output.contains("Visible posts: post1112, post2123, post3298"));
  }

  @Test
  public void testMenuChoiceSearchUsersByLocation() {
    String input = "1\n" + USER_INFO_FILE + "\n" + POST_INFO_FILE + "\n4\nNY\n5\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    SocialMediaApp.main(new String[]{});

    String output = outputStream.toString();
    assertTrue(output.contains("Matching users: John Doe"));
  }

  @Test
  public void testMenuChoiceExit() {
    String input = "5\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    SocialMediaApp.main(new String[]{});

    String output = outputStream.toString();
    assertTrue(output.contains("Exiting the program..."));
  }
}