package engine.validations;

import engine.users.User;
import java.util.HashMap;

public class UsersValidations {

    public static boolean isUserExistInTheSystem(HashMap<String, User> users, String userToCheck) {
        return users.containsKey(userToCheck);
    }

    public static boolean validateLoadMoneyIntoAccountInput(String amountToLoad, StringBuilder error) {
        try {
            Double.parseDouble(amountToLoad);
            return true;
        }
        catch (Exception ex) {
            error.append("Please insert legal decimal number");
            return false;
        }
    }
}
