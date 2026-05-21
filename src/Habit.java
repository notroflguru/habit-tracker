public class Habit {
    String habitName;
    String description;
    String frequency;

    Habit(String habitName, String description, String frequency) {
        this.habitName = habitName;
        this.description = description;
        this.frequency = frequency;
    }


    @Override
    public String toString() {
        return (habitName + " | " + description + " | " + frequency);
    }
}