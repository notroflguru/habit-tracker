public interface UserRepository {
    User findByLogin(String login);
    void addUser(User user);
}
