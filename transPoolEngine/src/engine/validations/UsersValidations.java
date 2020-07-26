package engine.validations;

import engine.users.User;
import java.util.HashMap;

public class UsersValidations {

    public static boolean isUserExistInTheSystem(HashMap<String, User> users, String userToCheck) {
        return users.containsKey(userToCheck);
    }

    public static void validateLoadMoneyIntoAccountInput(String amountToLoad, StringBuilder error) throws Exception {
        try {
            Double.parseDouble(amountToLoad);
        }
        catch (Exception ex) {
            throw new Exception("Please insert legal decimal number");
        }
    }
}
