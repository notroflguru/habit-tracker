import java.util.HashSet;
import java.util.Set;

public class Service{
    User user;

    // Habit set
    private Set<Habit> habitSet;

    Service(User currentUser) {
        this.user = currentUser;
        this.habitSet = new HashSet<>();
    }

    public Set<Habit> viewHabits() {
        return habitSet;
    }

    public Habit createHabit(String name, String description, String frequency) {
        Habit newHabit = new Habit(name, description, frequency);
        if (!frequency.equals("Ежедневно") && !frequency.equals("Еженедельно") && !frequency.equals("Ежемесячно")) {
            throw new IllegalArgumentException("Неверная частота");
        }
        if (habitSet.contains(newHabit)) {
            throw new IllegalArgumentException("Такая привычка уже существует");
        }
        habitSet.add(newHabit);
        return newHabit;
    }

}