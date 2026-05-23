public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userLogin(String login, String passwordHash) {
        User currentUser = userRepository.findByLogin(login);
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
        User user = userRepository.findByLogin(login);
        if (user != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует!");
        } else {
            User newUser = new User(login, passwordHash);
            userRepository.addUser(newUser);
            return newUser;
        }
    }
}