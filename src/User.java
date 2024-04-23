import java.util.List;

public class User {
  private String username;
  private String displayName;
  private String state;
  private List<String> friends;

  public User(String username, String displayName, String state, List<String> friends) {
    this.username = username;
    this.displayName = displayName;
    this.state = state;
    this.friends = friends;
  }

  public String getUsername() {
    return username;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getState() {
    return state;
  }

  public List<String> getFriends() {
    return friends;
  }
}