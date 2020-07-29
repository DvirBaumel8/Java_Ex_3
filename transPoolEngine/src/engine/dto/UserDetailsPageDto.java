package engine.dto;

import java.util.List;

public class UserDetailsPageDto {
    private List<MapsTableElementDetailsDto> mapsTableElementsInfo;
    private double userAccountBalance;
    private List<String> userAccountTransactions;

    public UserDetailsPageDto(List<MapsTableElementDetailsDto> mapsTableElementsInfo, double userAccountBalance, List<String> userAccountTransactions) {
        this.mapsTableElementsInfo = mapsTableElementsInfo;
        this.userAccountBalance = userAccountBalance;
    }
}
