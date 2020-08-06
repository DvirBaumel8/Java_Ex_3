package engine.dto.userPage;

public class LoadMoneyIntoAccountResponseDto {
    private String newBalance;
    private String errors;

    public LoadMoneyIntoAccountResponseDto(String newBalance, String error) {
        this.newBalance = newBalance;
        this.errors = error;
    }
}
