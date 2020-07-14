package Engine.UsersManagment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersManager {

    private final List<User> users;

    public UsersManager() {
        users = new ArrayList<>();
    }

    public synchronized void addUser(User user) {
        users.add(user);
    }

    public synchronized void removeUser(String username) {
        users.remove(username);
    }

    public synchronized List<String> getUsersNames() {
        return users.stream().map(User::getUserName).collect(Collectors.toList());
    }

    public boolean isUserExists(String username) {
        for(User user : users) {
            if(user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
