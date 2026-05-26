import java.util.ArrayList;

public class Service{
    User user;
    private final HabitRepository habitRepository;

    Service(User currentUser, HabitRepository habitRepository) {
        this.user = currentUser;
        this.habitRepository = habitRepository;
    }

    public ArrayList<Habit> viewHabits() {
        return habitRepository.findAll();
    }

    public void createHabit(String name, String description, String frequency) {
        Habit newHabit = new Habit(name, description, frequency.toLowerCase());
        if (!newHabit.getFrequency().equals("ежедневно") && !newHabit.getFrequency().equals("еженедельно") && !newHabit.getFrequency().equals("ежемесячно")) {
            throw new RuntimeException("Неверная частота");
        }
        habitRepository.add(newHabit);
    }

    public void deleteHabit(int id) {
        Habit habit = getHabitById(id);
        if (habit == null) {
            System.out.println("Такой привычки не существует!");
            return;
        }
        habitRepository.delete(habit);
        System.out.println("Успешно удалили привычку " + habit);
    }

    public Habit getHabitById(int id) {
        return habitRepository.getHabitById(id);
    }

}