package engine.users;

import engine.dto.userPage.UserTransactionsHistoryDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersManager {
    private HashMap<String, User> users;

    public UsersManager() {
        users = new HashMap<>();
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
        return users.containsKey(userToCheck);
    }

    public List<UserTransactionsHistoryDto> getUserTransactionsDtoFromEngine(String userName) {
        List<Transaction> userTransactions = users.get(userName).getUserTransactions();
        List<UserTransactionsHistoryDto> userTransactionsHistoryDtoList = new LinkedList<>();

        userTransactions.forEach((userTransaction) -> {
            UserTransactionsHistoryDto userTransactionsHistoryDto = new
                    UserTransactionsHistoryDto(userTransaction.getTransactionType().toString(),
                    userTransaction.getTransactionDate().toString(), String.valueOf(userTransaction.getAmountOfTransfer()),
                    String.valueOf(userTransaction.getBalanceBeforeTransaction()),
                    String.valueOf(userTransaction.getBalanceAfterTransaction()));
            userTransactionsHistoryDtoList.add(userTransactionsHistoryDto);
        });

        return userTransactionsHistoryDtoList;
    }
}
