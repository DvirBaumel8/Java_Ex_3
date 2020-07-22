package engine.users;

import engine.validations.UsersValidations;

import java.util.HashMap;

public class UsersManager {
    private static HashMap<String, User> users;
    private static UsersValidations usersValidations;


    public UsersManager() {
        users = new HashMap<>();
        usersValidations = new UsersValidations();
    }

    public void addNewUser(String userName, User user) {
        users.put(userName, user);
    }

    public void loadMoneyIntoUserAccount(String userName, double moneyToLoad) {
        User user = users.get(userName);
        user.loadMoneyIntoAccount(moneyToLoad, java.time.LocalDate.now());
    }

    public void addTransactionToUserByUserName(String userName, Transaction transaction) {
        users.get(userName).addTransaction(transaction);
    }

    public User getUserByName(String userName) {
        return users.get(userName);
    }

    public double getCurrentUserCashByUserName(String userName) {
        return users.get(userName).getCurrentCash();
    }

    public boolean isUserExistInTheSystem(String userToCheck) {
        return usersValidations.isUserExistInTheSystem(users, userToCheck);
    }
}
