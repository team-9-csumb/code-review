import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SocialMediaApp {
  Map<String, User> users;
  Map<String, Post> posts;

  public SocialMediaApp() {
    users = new HashMap<>();
    posts = new HashMap<>();
  }

  public void loadData(String userInfoFile, String postInfoFile) {
    try {
      BufferedReader userReader = new BufferedReader(new FileReader(userInfoFile));
      String line;
      while ((line = userReader.readLine()) != null) {
        String[] userData = line.split(";");
        String username = userData[0];
        String displayName = userData[1];
        String state = userData[2];
        String[] friendsList = userData[3].substring(1, userData[3].length() - 1).split(",");
        User user = new User(username, displayName, state, Arrays.asList(friendsList));
        users.put(username, user);
      }
      userReader.close();

      BufferedReader postReader = new BufferedReader(new FileReader(postInfoFile));
      while ((line = postReader.readLine()) != null) {
        String[] postData = line.split(";");
        String postId = postData[0];
        String userId = postData[1];
        String visibility = postData[2];
        Post post = new Post(postId, userId, visibility);
        posts.put(postId, post);
      }
      postReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean checkVisibility(String postId, String username) {
    Post post = posts.get(postId);
    if (post == null) {
      return false;
    }
    if (post.getVisibility().equals("public")) {
      return true;
    }
    User user = users.get(username);
    User postOwner = users.get(post.getUserId());
    return user != null && postOwner != null && postOwner.getFriends().contains(username);
  }

  public List<String> retrievePosts(String username) {
    List<String> visiblePosts = new ArrayList<>();
    for (Post post : posts.values()) {
      if (!post.getUserId().equals(username) && checkVisibility(post.getPostId(), username)) {
        visiblePosts.add(post.getPostId());
      }
    }
    return visiblePosts;
  }

  public List<String> searchUsersByLocation(String state) {
    List<String> matchingUsers = new ArrayList<>();
    for (User user : users.values()) {
      if (user.getState().equals(state)) {
        matchingUsers.add(user.getDisplayName());
      }
    }
    return matchingUsers;
  }

  public static void main(String[] args) {
    SocialMediaApp app = new SocialMediaApp();
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Menu:");
      System.out.println("1. Load input data");
      System.out.println("2. Check visibility");
      System.out.println("3. Retrieve posts");
      System.out.println("4. Search users by location");
      System.out.println("5. Exit");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline character

      switch (choice) {
        case 1:
          System.out.print("Enter the path of user information file: ");
          String userInfoFile = scanner.nextLine();
          System.out.print("Enter the path of post information file: ");
          String postInfoFile = scanner.nextLine();
          app.loadData(userInfoFile, postInfoFile);
          System.out.println("Data loaded successfully.");
          break;
        case 2:
          System.out.print("Enter a post ID: ");
          String postId = scanner.nextLine();
          System.out.print("Enter a username: ");
          String username = scanner.nextLine();
          boolean canView = app.checkVisibility(postId, username);
          System.out.println(canView ? "Access Permitted" : "Access Denied");
          break;
        case 3:
          System.out.print("Enter a username: ");
          username = scanner.nextLine();
          List<String> visiblePosts = app.retrievePosts(username);
          System.out.println("Visible posts: " + String.join(", ", visiblePosts));
          break;
        case 4:
          System.out.print("Enter a state: ");
          String state = scanner.nextLine();
          List<String> matchingUsers = app.searchUsersByLocation(state);
          System.out.println("Matching users: " + String.join(", ", matchingUsers));
          break;
        case 5:
          System.out.println("Exiting the program...");
          System.exit(0);
        default:
          System.out.println("Invalid choice. Please try again.");
      }
      System.out.println();
    }
  }
}