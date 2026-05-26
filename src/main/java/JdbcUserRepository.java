import java.sql.*;

public class JdbcUserRepository implements UserRepository{
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;


    JdbcUserRepository(String url, String user, String password) {
        this.dbUrl = url;
        this.dbUser = user;
        this.dbPassword = password;
    }


    private Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        return connection;
    }


    public User findByLogin(String login) {
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT id, login, password_hash FROM users WHERE login = ?")){
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("login"), rs.getString("password_hash"));
                    user.setId(rs.getInt("id"));
                    return user;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти пользователя", e);
        }
    }


    public void addUser(User user) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (login, password_hash) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPasswordHash());
            int result = statement.executeUpdate();
            if (result != 1) {
                throw new SQLException("Ничего не выполнено");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    user.setId(id);
                } else {
                    System.out.println("Произошла ошибка generatedKeys");
                    throw new RuntimeException("GeneratedKeys пуст!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка! " + e.getMessage());
            throw new RuntimeException("Не удалось добавить пользователя", e);
        }

    }
}
