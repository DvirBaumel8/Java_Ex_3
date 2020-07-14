package Engine.UsersManagment;

public class User {
    private String userName;
    private UserType userType;
    private Account userAccount;

    public User(String userName, String userTypeEnum) {
        this.userName = userName;
        switch (userName) {
            case "Requester": {
                this.userType = UserType.Requester;
                break;
            }
            case "Suggester": {
                this.userType = UserType.Suggester;
                break;
            }
        }

        this.userAccount = new Account();
    }

    public String getUserName() {
        return userName;
    }

    public enum UserType {
        Requester,
        Suggester
    }
}
