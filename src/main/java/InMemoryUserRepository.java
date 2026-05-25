import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository{
    private Map<String, User> users = new HashMap<>();

    public User findByLogin(String login) {
        return users.get(login);
    }

    public void addUser(User user) {
        users.put(user.getLogin(), user);
    }
}
