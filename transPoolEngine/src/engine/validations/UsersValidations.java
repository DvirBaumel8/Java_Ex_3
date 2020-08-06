package engine.validations;

public class UsersValidations {

    public static String validateLoadMoneyIntoAccountInput(String amountToLoadStr) {
        try {
            Double.parseDouble(amountToLoadStr);
        }
        catch (Exception ex) {
            return "Please insert legal decimal number";
        }
        double amountToLoad = Double.parseDouble(amountToLoadStr);
        if(amountToLoad <= 0) {
            return "Not possible to load minus number or zero.";
        }

        return "";
    }

    public static boolean validateUserType(String userType, StringBuilder errorStr) {
        if(userType == null) {
            errorStr.append("Please choose user type");
            return false;
        }
        else if(userType.equals("requestPassenger") || userType.equals("suggestPassenger")) {
            return true;
        }
        errorStr.append("Please choose user type");
        return false;
    }

    public static boolean validateUserLoginParams(String userName, String userTpe, StringBuilder errors) {
        if(userName == null || userName.isEmpty()) {
            errors.append("User name empty");
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
