package bg.sofia.uni.fmi.jira;

public class User {
    private String userName;

    public User(String userName) {
        ValidateNullValues.validatePar(new Object[] { userName });
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
