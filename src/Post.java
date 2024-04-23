public class Post {
  private String postId;
  private String userId;
  private String visibility;

  public Post(String postId, String userId, String visibility) {
    this.postId = postId;
    this.userId = userId;
    this.visibility = visibility;
  }

  public String getPostId() {
    return postId;
  }

  public String getUserId() {
    return userId;
  }

  public String getVisibility() {
    return visibility;
  }
}