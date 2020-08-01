package engine.manager;

import engine.dto.userPage.MapsTableElementDetailsDto;
import engine.dto.userPage.UserTransactionsHistoryDto;

import java.util.List;

public class UserDetailsDto {
    private List<MapsTableElementDetailsDto> mapsTableElementsInfo;
    private String userBalanceDto;
    private List<UserTransactionsHistoryDto> userAccountTransactions;

    public UserDetailsDto(List<MapsTableElementDetailsDto> mapsTableElementsInfo, String userBalanceDto,
                          List<UserTransactionsHistoryDto> userAccountTransactions) {
        this.mapsTableElementsInfo = mapsTableElementsInfo;
        this.userBalanceDto = userBalanceDto;
        this.userAccountTransactions = userAccountTransactions;
    }

    public List<MapsTableElementDetailsDto> getMapsTableElementsInfo() {
        return mapsTableElementsInfo;
    }

    public void setMapsTableElementsInfo(List<MapsTableElementDetailsDto> mapsTableElementsInfo) {
        this.mapsTableElementsInfo = mapsTableElementsInfo;
    }

    public String getUserBalanceDto() {
        return userBalanceDto;
    }

    public void setUserBalanceDto(String userBalanceDto) {
        this.userBalanceDto = userBalanceDto;
    }

    public List<UserTransactionsHistoryDto> getUserAccountTransactions() {
        return userAccountTransactions;
    }

    public void setUserAccountTransactions(List<UserTransactionsHistoryDto> userAccountTransactions) {
        this.userAccountTransactions = userAccountTransactions;
    }
}
