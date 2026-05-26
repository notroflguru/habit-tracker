import java.util.ArrayList;

public interface HabitRepository {
    void add(Habit habit);
    ArrayList<Habit> findAll();
    Habit getHabitById(int id);
    void delete(Habit habit);
}
