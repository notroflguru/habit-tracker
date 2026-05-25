public class User {
    private int id;
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
    public int getUserId() {
        return id;
    }

    // Сеттеры
    public void setId(int newId) {
        this.id = newId;
    }
}