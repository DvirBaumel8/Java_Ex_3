package engine.dto;

import java.util.List;

public class UserMapDetailsPageDto {
    private List<MapsTableElementDetailsDto> mapsTableElementsInfo;
    private double userAccountBalance;

    public UserMapDetailsPageDto(List<MapsTableElementDetailsDto> mapsTableElementsInfo, double userAccountBalance) {
        this.mapsTableElementsInfo = mapsTableElementsInfo;
        this.userAccountBalance = userAccountBalance;
    }
}
