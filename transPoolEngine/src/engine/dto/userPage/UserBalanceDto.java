package engine.dto.userPage;

public class UserBalanceDto {
    private double userBalance;

    public UserBalanceDto(double userBalance) {
        this.userBalance = userBalance;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }
}
