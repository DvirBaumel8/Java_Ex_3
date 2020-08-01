package engine.users;

import java.time.LocalDate;

public class Transaction {
    private TransactionType transactionType;
    private LocalDate transactionDate;
    private double amountOfTransfer;
    private double balanceBeforeTransaction;
    private double balanceAfterTransaction;

    public Transaction(TransactionType transactionType, LocalDate transactionDate, double amountOfTransfer, double balanceBeforeTransaction, double balanceAfterTransaction) {
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

    @Override
    public String toString() {
        return String.format( "Type: %s Date: %s Money: %f Balance before transaction: %f Balance after transaction: %f", transactionType.toString(), transactionDate.toString(), amountOfTransfer, balanceBeforeTransaction, balanceAfterTransaction);
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmountOfTransfer() {
        return amountOfTransfer;
    }

    public void setAmountOfTransfer(double amountOfTransfer) {
        this.amountOfTransfer = amountOfTransfer;
    }

    public double getBalanceBeforeTransaction() {
        return balanceBeforeTransaction;
    }

    public void setBalanceBeforeTransaction(double balanceBeforeTransaction) {
        this.balanceBeforeTransaction = balanceBeforeTransaction;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(double balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
}
