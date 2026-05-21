import java.util.*;

public class Main {
    private static Scanner console = new Scanner(System.in);
    private static Service service;
    private static authService authService = new authService();
    public static void main(String[] args) {

        User currentUser = null;
        while (currentUser==null) {
            currentUser = loginMenu();
            service = new Service(currentUser);
        }

        while (true) {
            mainMenu();
        }

    }


    private static Habit createHabit() {
        System.out.println("Введите название новой привычки");
        String newName = console.nextLine();
        System.out.println("Введите описание привычки");
        String newDescription = console.nextLine();
        System.out.println("Введите частоту выполнения привычки (ежедневно, еженедельно или ежемесячно)");
        String newFrequency = console.nextLine();
        try {
            Habit newHabit = service.createHabit(newName, newDescription, newFrequency);
            return newHabit;
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
        return null;
    }


    private static User loginMenu() {
        System.out.println("1. Вход");
        System.out.println("2. Регистрация");
        System.out.println("0. Выход");

        Byte command = console.nextByte();
        console.nextLine();
        switch (command) {
            default: System.out.println("Команда не распознана!"); return null;
            case 0: System.exit(0);
            case 1: return userLogin();
            case 2: return userRegister();
        }
    }


    private static User userLogin() {
        System.out.println("Введите логин:");
        String login = console.nextLine();
        System.out.println("Введите пароль:");
        String passwordHash = console.nextLine();
        try {
            User currentUser = authService.userLogin(login, passwordHash);
            return currentUser;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    private static User userRegister() {
        System.out.println("Введите логин:");
        String login = console.nextLine();
        System.out.println("Введите пароль:");
        String passwordHash = console.nextLine();
        try {
            User newUser = authService.userRegister(login, passwordHash);
            System.out.println("Новый пользователь успешно зарегистрирован!");
            return newUser;
        } catch (IllegalArgumentException iae) {
            System.out.print(iae.getMessage());
        }
        return null;
    }


    private static void mainMenu() {
        System.out.println("=== Трекер привычек ===");
        System.out.println("1. Создать привычку");
        System.out.println("2. Посмотреть привычки");
        System.out.println("0. Выход");

        Byte command = console.nextByte();
        console.nextLine();
        switch (command) {
            default: System.out.println("Команда не распознана!"); return;
            case 0: System.exit(0);
            case 1: createHabit(); break;
            case 2: viewHabits(); break;
        }
    }


    private static void viewHabits() {
        System.out.println(service.viewHabits());
    }
}


class Habit {
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
        return (habitName + " | " + description + " | " + frequency + " ");
    }
}


class User {
    private String login;
    private String passwordHash;

    User(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }


    // Геттеры
    public String getPasswordHash() {
        return passwordHash;
    }
    public String getLogin() {
        return login;
    }
}


class Service{
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


class authService{
    private static Map<String, User> users = new HashMap<>();

    public User userLogin(String login, String passwordHash) {
        User currentUser = users.get(login);
        if (currentUser == null) {
            throw new IllegalArgumentException("Данный пользователь не найден!");
        }
        if (currentUser.getPasswordHash().equals(passwordHash)) {
            return currentUser;
        } else {
            throw new IllegalArgumentException("Неверный пароль!");
        }
    }


    public User userRegister(String login, String passwordHash) {
        User currentUser = users.get(login);
        if (currentUser != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует!");
        } else {
            User newUser = new User(login, passwordHash);
            users.put(login, newUser);
            return newUser;
        }
    }
}