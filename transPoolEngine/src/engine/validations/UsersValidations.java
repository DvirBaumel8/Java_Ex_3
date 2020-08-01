package engine.validations;

public class UsersValidations {

    public static void validateLoadMoneyIntoAccountInput(String amountToLoad, StringBuilder error) throws Exception {
        try {
            Double.parseDouble(amountToLoad);
        }
        catch (Exception ex) {
            throw new Exception("Please insert legal decimal number");
        }
    }

    public static boolean validateUserType(String userType, StringBuilder errorStr) {
        if(userType.equals("requestPassenger") || userType.equals("suggestPassenger")) {
            return true;
        }
        errorStr.append("Please choose user type\n");
        return false;
    }

    public static boolean validateUserLoginParams(String userName, String userTpe, StringBuilder errors) {
        if(userName == null || userName.isEmpty()) {
            errors.append("User name emtyp\n");
            return false;
        }
        else if(UsersValidations.validateUserType(userTpe, errors)) {
            return true;
        }
        else {
            return false;
        }
    }
}
