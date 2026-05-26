import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userLogin(String login, String password) {
        try {
            User currentUser = userRepository.findByLogin(login);
            if (currentUser == null) {
                throw new RuntimeException("Пользователя с таким логином не существует");
            }
            if (BCrypt.checkpw(password, currentUser.getPasswordHash())) {
                return currentUser;
            } else {
                throw new RuntimeException("Неверный пароль!");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public User userRegister(String login, String password) {
        try {
            String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = userRepository.findByLogin(login);
            if (user != null) {
                throw new IllegalArgumentException("Пользователь с таким логином уже существует!");
            } else {
                User newUser = new User(login, passwordHash);
                userRepository.addUser(newUser);
                return newUser;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}