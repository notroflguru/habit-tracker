package Repositories;


import java.util.Set;

public interface HabitRepository {
    public Habit add(Habit habit);
    public Set<Habit> findAll();
    public Habit exists(Habit habit);
}
