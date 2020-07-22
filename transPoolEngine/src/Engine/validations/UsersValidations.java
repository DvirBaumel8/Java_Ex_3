package Engine.validations;

import Engine.users.User;
import java.util.HashMap;

public class UsersValidations {

    public boolean isUserExistInTheSystem(HashMap<String, User> users, String userToCheck) {
        return users.containsKey(userToCheck);
    }
}
