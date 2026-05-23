import java.util.Collection;

public class Service{
    User user;
    private final HabitRepository habitRepository;

    Service(User currentUser, HabitRepository habitRepository) {
        this.user = currentUser;
        this.habitRepository = habitRepository;
    }

    public Collection<Habit> viewHabits() {
        return habitRepository.findAll();
    }

    public void createHabit(String name, String description, String frequency) {
        Habit newHabit = new Habit(name, description, frequency.toLowerCase());
        if (!newHabit.getFrequency().equals("ежедневно") && !newHabit.getFrequency().equals("еженедельно") && !newHabit.getFrequency().equals("ежемесячно")) {
            throw new IllegalArgumentException("Неверная частота");
        }
        if (!habitRepository.existsByName(newHabit.getHabitName())) {
        habitRepository.add(newHabit);}
        else {throw new IllegalArgumentException("Ошибка. Такая привычка уже существует");}
    }

    public void deleteHabit(String name) {
        Habit habit = getHabitByName(name);
        habitRepository.delete(habit);
        System.out.println("Успешно удалили привычку " + habit);
    }

    public Habit getHabitByName(String name) {
        return habitRepository.getHabitByName(name);
    }

}