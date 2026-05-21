import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static Map<String, User> users = new HashMap<>();

    public User userLogin(String login, String passwordHash) {
        User currentUser = users.get(login);
        if (currentUser == null) {
            throw new IllegalArgumentException("Данный пользователь не найден!");
        }
        if (currentUser.getPasswordHash().equals(passwordHash)) {
            return currentUser;
        } else {
            throw new IllegalArgumentException("Неверный пароль!");
        }
    }


    public User userRegister(String login, String passwordHash) {
        User currentUser = users.get(login);
        if (currentUser != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует!");
        } else {
            User newUser = new User(login, passwordHash);
            users.put(login, newUser);
            return newUser;
        }
    }
}