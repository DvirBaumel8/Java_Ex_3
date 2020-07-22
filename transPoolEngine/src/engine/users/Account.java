package engine.users;



import com.google.gson.Gson;

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

    public String getAccountTransactions() {
        Gson gson = new Gson();
        return gson.toJson(accountTransactions);
    }

    public double getCurrentCash() {
        return money;
    }
}
