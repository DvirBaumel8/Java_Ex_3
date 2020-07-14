package Engine.UsersManagment;

public class User {
    private String userName;
    private UserType userTypeEnum;
    private Account userAccount;

    public String getUserName() {
        return userName;
    }

    public enum UserType {
        REQUESTER,
        Suggester
    }
}
