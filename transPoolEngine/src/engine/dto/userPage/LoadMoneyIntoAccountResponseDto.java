package engine.dto.userPage;

public class LoadMoneyIntoAccountResponseDto {
    private String newBalance;
    private String error;

    public LoadMoneyIntoAccountResponseDto(String newBalance, String error) {
        this.newBalance = newBalance;
        this.error = error;
    }
}
