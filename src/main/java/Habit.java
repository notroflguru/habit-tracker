public class Habit {
    private int id;
    private String habitName;
    private String description;
    private String frequency;

    Habit(String habitName, String description, String frequency) {
        this.habitName = habitName.toLowerCase();
        this.description = description;
        this.frequency = frequency;
    }

    // Геттеры
    public String getHabitName() {
        return habitName;
    }

    public String getDescription() {
        return description;
    }

    public String getFrequency() {
        return frequency;
    }

    public int getId() {
        return id;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return (id + " | " + habitName + " | " + description + " | " + frequency);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {return false;}
        Habit habit = (Habit) obj;
        String tempHabitName = habit.getHabitName();
        return tempHabitName.equals(habitName);
    }
}