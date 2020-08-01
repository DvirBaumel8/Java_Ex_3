package engine.dto;

public class UserTransactionsHistoryDto {
    String type;
    String date;
    String actionAmount;
    String amountBeforeAction;
    String amountAfterAction;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActionAmount() {
        return actionAmount;
    }

    public void setActionAmount(String actionAmount) {
        this.actionAmount = actionAmount;
    }

    public String getAmountBeforeAction() {
        return amountBeforeAction;
    }

    public void setAmountBeforeAction(String amountBeforeAction) {
        this.amountBeforeAction = amountBeforeAction;
    }

    public String getAmountAfterAction() {
        return amountAfterAction;
    }

    public void setAmountAfterAction(String amountAfterAction) {
        this.amountAfterAction = amountAfterAction;
    }
}
