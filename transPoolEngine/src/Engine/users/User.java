package Engine.users;

import java.time.LocalDate;

public class User {
    private String userName;
    private UserType userType;
    private Account userAccount;

    public User(String userName, String userTypeEnum) {
        this.userName = userName;
        switch (userTypeEnum) {
            case "Requester": {
                this.userType = UserType.Requester;
                break;
            }
            case "Suggester": {
                this.userType = UserType.Suggester;
                break;
            }
        }

        this.userAccount = new Account();
    }

    public String getUserName() {
        return userName;
    }

    public void loadMoneyIntoAccount(double moneyToLoad, LocalDate date) {
        Transaction transaction = new Transaction(Transaction.TransactionType.LoadMoney, date, moneyToLoad, userAccount.getCurrentCash(), userAccount.getCurrentCash() + moneyToLoad);
        userAccount.addMoney(moneyToLoad);
        userAccount.addTransaction(transaction);
    }

    public void addTransaction(Transaction transaction) {
        userAccount.addTransaction(transaction);
    }

    public void takeMoney(double totalCost) {
        userAccount.takeMoney(totalCost);
    }

    public void addMoney(double totalCost) {
        userAccount.addMoney(totalCost);
    }

    public enum UserType {
        Requester,
        Suggester
    }

    public String getUserTransactions() {
        return userAccount.getAccountTransactions();
    }

    public double getCurrentCash() {
        return userAccount.getCurrentCash();
    }

}
