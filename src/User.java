public class User {
    private String login;
    private String passwordHash;

    User(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }


    // Геттеры
    public String getPasswordHash() {
        return passwordHash;
    }
    public String getLogin() {
        return login;
    }
}