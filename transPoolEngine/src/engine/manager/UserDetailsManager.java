package engine.manager;

import engine.dto.userPage.MapsTableElementDetailsDto;
import engine.dto.userPage.UserBalanceDto;
import engine.dto.userPage.UserTransactionsHistoryDto;

import java.util.List;

public class UserDetailsManager {
    private List<MapsTableElementDetailsDto> mapsTableElementsInfo;
    private UserBalanceDto userBalanceDto;
    private List<UserTransactionsHistoryDto> userAccountTransactions;

    public UserDetailsManager(List<MapsTableElementDetailsDto> mapsTableElementsInfo, UserBalanceDto userBalanceDto,
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

    public UserBalanceDto getUserBalanceDto() {
        return userBalanceDto;
    }

    public void setUserBalanceDto(UserBalanceDto userBalanceDto) {
        this.userBalanceDto = userBalanceDto;
    }

    public List<UserTransactionsHistoryDto> getUserAccountTransactions() {
        return userAccountTransactions;
    }

    public void setUserAccountTransactions(List<UserTransactionsHistoryDto> userAccountTransactions) {
        this.userAccountTransactions = userAccountTransactions;
    }
}
