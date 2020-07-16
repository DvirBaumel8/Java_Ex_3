package manager;

import java.util.HashMap;
import java.util.Map;

public class UserManagerDto {

    private final Map<String, String> usersHashMap;

    public UserManagerDto() {
        usersHashMap = new HashMap<>();
    }

    public synchronized void addUser(String username, String userType) {
        usersHashMap.put(username,userType);
    }

    public synchronized void removeUser(String username) {
        usersHashMap.remove(username);
    }

    public synchronized Map<String, String> getUsers() {
        return usersHashMap;
    }

    public boolean isUserExists(String username) {
        return usersHashMap.containsKey(username);
    }
}

