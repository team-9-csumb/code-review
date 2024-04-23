#include <iostream>
#include <unordered_map>
#include <vector>
#include <algorithm>

using namespace std;

struct UserInfo {
    string displayName;
    string state;
    vector<string> friends;
};

struct PostInfo {
    string userID;
    string visibility;
};

unordered_map<string, UserInfo> userData;
unordered_map<string, PostInfo> postData;

void loadInputData() {
    cout << "Data loaded successfully!" << endl;
}

string checkVisibility(string postId, string username) {
    if (postData.find(postId) == postData.end()) {
        return "Post ID not found";
    }

    PostInfo postInfo = postData[postId];

    if (postInfo.visibility == "public") {
        return "Access Permitted";
    } else if (postInfo.visibility == "friend") {
        if (find(userData[postInfo.userID].friends.begin(), userData[postInfo.userID].friends.end(), username) != userData[postInfo.userID].friends.end()) {
            return "Access Permitted";
        } else {
            return "Access Denied";
        }
    }
    return "Unknown visibility type";
}

vector<string> retrievePosts(string username) {
    vector<string> accessiblePosts;

    for (pair<string, PostInfo> post : postData) {
        if (post.second.userID != username) {
            if (post.second.visibility == "public" || find(userData[post.second.userID].friends.begin(), userData[post.second.userID].friends.end(), username) != userData[post.second.userID].friends.end()) {
                accessiblePosts.push_back(post.first);
            }
        }
    }

    return accessiblePosts;
}

vector<string> location(string state) {
    vector<string> usersInState;

    for (pair<string, UserInfo> user : userData) {
        if (user.second.state == state) {
            usersInState.push_back(user.second.displayName);
        }
    }

    return usersInState;
}

int main() {
    userData = {
        {"goldenlover1", {"Jane Doe", "CA", {"petpal4ever", "whiskerwatcher"}}},
        {"whiskerwatcher", {"John Doe", "NY", {"goldenlover1"}}},
        {"petpal4ever", {"Great Name", "WV", {"goldenlover1"}}}
    };

    postData = {
        {"post1112", {"goldenlover1", "friend"}},
        {"post2123", {"whiskerwatcher", "friend"}},
        {"post3298", {"petpal4ever", "public"}}
    };

    cout << "Welcome to the Social Network Platform!" << endl;

    while (true) {
        cout << "\nMenu:" << endl;
        cout << "1. Load input data" << endl;
        cout << "2. Check visibility" << endl;
        cout << "3. Retrieve posts" << endl;
        cout << "4. Search users by location" << endl;
        cout << "5. Exit" << endl;

        int choice;
        cout << "\nEnter your choice: ";
        cin >> choice;

        switch (choice) {
            case 1: {
                loadInputData();
                break;
            }
                
            case 2: {
                string postId, username;

                cout << "Enter the post ID: ";
                cin >> postId;

                cout << "Enter your username: ";
                cin >> username;

                cout << checkVisibility(postId, username) << endl;
                break;
            }
                
            case 3: {
                string username;

                cout << "Enter your username: ";
                cin >> username;

                vector<string> posts = retrievePosts(username);

                cout << "Accessible posts:" << endl;
                for (string post : posts) {
                    cout << post << endl;
                }
                break;
            }
                
            case 4: {
                string state;

                cout << "Enter the state location: ";
                cin >> state;

                vector<string> users = location(state);

                cout << "Users in " << state << ":" << endl;
                for (string user : users) {
                    cout << user << endl;
                }
                break;
            }
                
            case 5:
                cout << "Exiting the program. Goodbye!" << endl;
                return 0;
                
            default:
                cout << "Invalid choice. Please try again." << endl;
        }
    }

    return 0;
}
