import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PostTest {
  @Test
  public void testPost() {
    Post post = new Post("postId", "userId", "public");

    assertEquals("postId", post.getPostId());
    assertEquals("userId", post.getUserId());
    assertEquals("public", post.getVisibility());
  }
}
