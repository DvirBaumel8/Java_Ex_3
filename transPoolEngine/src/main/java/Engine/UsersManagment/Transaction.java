package Engine.UsersManagment;

import java.util.Date;

public class Transaction {
    private TransactionType transactionType;
    private Date transactionDate;
    private double amountOfTransfer;
    private double balanceBeforeTransaction;
    private double balanceAfterTransaction;

    public Transaction(TransactionType transactionType, Date transactionDate, double amountOfTransfer, double balanceBeforeTransaction, double balanceAfterTransaction) {
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.amountOfTransfer = amountOfTransfer;
        this.balanceBeforeTransaction = balanceBeforeTransaction;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public enum TransactionType {
        LoadMoney,
        PaymentReceiving,
        PaymentTransfer
    }
}
