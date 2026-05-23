import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHabitRepository implements HabitRepository {

    Map<String, Habit> habitMap = new HashMap<>();

    public void add(Habit habit) {
        habitMap.put(habit.getHabitName(), habit);
    }

    public List<Habit> findAll() {
        return List.copyOf(habitMap.values());
    }


    public boolean existsByName(String name) {
        return habitMap.get(name) != null;
    }


    public Habit getHabitByName(String name) {
        return habitMap.get(name);
    }
    public void delete(Habit habit) {
        String name = habit.getHabitName();
        if (existsByName(name)) {
            habitMap.remove(name);
        }
    }
}
