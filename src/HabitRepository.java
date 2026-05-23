import java.util.Collection;

public interface HabitRepository {
    void add(Habit habit);
    Collection<Habit> findAll();
    boolean existsByName(String name);
    Habit getHabitByName(String name);
    void delete(Habit habit);
}
