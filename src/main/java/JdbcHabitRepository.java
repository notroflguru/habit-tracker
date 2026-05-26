import java.util.ArrayList;
import java.sql.*;

public class JdbcHabitRepository implements HabitRepository {

    private final String dbUrl;
    private final int userId;
    private final String dbUser;
    private final String dbPassword;

    JdbcHabitRepository(String url, String user, String password, int id) {
        this.dbUrl = url;
        this.dbUser = user;
        this.dbPassword = password;
        this.userId = id;
    }


    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        return connection;
    }


    public void add(Habit habit) {
        String sql = "INSERT INTO habits (name, description, frequency, user_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, habit.getHabitName());
            statement.setString(2, habit.getDescription());
            statement.setString(3, habit.getFrequency());
            statement.setInt(4, this.userId);
            int result = statement.executeUpdate();
            if (result!=1) {
                throw new SQLException("Ничего не выполнено");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    habit.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Не удалось получить сгенерированный id для привычки");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public ArrayList<Habit> findAll() {
        String sql = "SELECT id, name, description, frequency FROM habits WHERE user_id = ?";
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, this.userId);
            try (ResultSet rs = statement.executeQuery()) {
                ArrayList<Habit> habits = new ArrayList<>();
                while (rs.next()) {
                    Habit newHabit = new Habit(rs.getString("name"), rs.getString("description"), rs.getString("frequency"));
                    newHabit.setId(rs.getInt("id"));
                    habits.add(newHabit);
                }
                return habits;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public Habit getHabitById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, name, description, frequency FROM habits WHERE (id, user_id) = (?, ?)")) {
            statement.setInt(1, id);
            statement.setInt(2, this.userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Habit habit = new Habit(rs.getString("name"), rs.getString("description"), rs.getString("frequency"));
                    habit.setId(rs.getInt("id"));
                    return habit;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public void delete(Habit habit) {
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM habits WHERE (user_id, name) = (?, ?)")) {
            statement.setInt(1, this.userId);
            statement.setString(2, habit.getHabitName());
            int result = statement.executeUpdate();
            if (result==1) {
                System.out.println("Привычка " + habit.getHabitName() + " удалена");
            } else {
                throw new SQLException("Не удалось удалить привычку");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
