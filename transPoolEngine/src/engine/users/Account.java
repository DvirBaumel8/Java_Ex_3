package engine.users;


import java.util.List;

public class Account {
    private double money;
    private List<Transaction> accountTransactions;

    public void addTransaction(Transaction transaction) {
        accountTransactions.add(transaction);
    }

    public void addMoney(double amountToAdd) {
        this.money += money;
    }

    public void takeMoney(double amountToTake) {
        this.money -= amountToTake;
    }

    public List<Transaction> getAccountTransactions() {
        return accountTransactions;
    }

    public double getCurrentCash() {
        return money;
    }
}
