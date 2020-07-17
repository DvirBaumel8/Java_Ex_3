package Engine.users;

import java.util.List;

public class Account {
    private double money;
    private List<Transaction> allTransactions;

    public void addTransaction(Transaction transaction) {
        allTransactions.add(transaction);
    }

    public void addMoney(double amountToAdd) {
        this.money += money;
    }

    public void takeMoney(double amountToTake) {
        this.money -= amountToTake;
    }
}
