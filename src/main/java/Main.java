import java.util.*;

public class Main {
    private static Scanner console = new Scanner(System.in);
    private static Service service;
    private static UserRepository userRepo = new JdbcUserRepository(
            "jdbc:postgresql://localhost:5432/habit_tracker",
            "postgres",
            "secret"
    );
    private static AuthService authService;
    public static void main(String[] args) {
        authService = new AuthService(userRepo);
        User currentUser = null;
        while (currentUser==null) {
            currentUser = loginMenu();
        }
        System.out.println("Вы успешно вошли, " + currentUser.getLogin());
        HabitRepository habitRepo = new JdbcHabitRepository(
                "jdbc:postgresql://localhost:5432/habit_tracker",
                "postgres",
                "secret",
                currentUser.getUserId()
        );

        service = new Service(currentUser, habitRepo);

        while (true) {
            mainMenu();
        }

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
        System.out.println("3. Удалить привычку");
        System.out.println("0. Выход");

        Byte command = console.nextByte();
        console.nextLine();
        switch (command) {
            default: System.out.println("Команда не распознана!"); return;
            case 0: System.exit(0);
            case 1: createHabit(); break;
            case 2: viewHabits(); break;
            case 3: deleteHabit(); break;
        }
    }


    private static void createHabit() {
        System.out.println("Введите название новой привычки");
        String newName = console.nextLine();
        System.out.println("Введите описание привычки");
        String newDescription = console.nextLine();
        System.out.println("Введите частоту выполнения привычки (ежедневно, еженедельно или ежемесячно)");
        String newFrequency = console.nextLine();
        try {
            service.createHabit(newName, newDescription, newFrequency.toLowerCase());
            System.out.println("Привычка [" + newName + " | " + newDescription + " | " + newFrequency + "] успешно создана!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void viewHabits() {
        ArrayList<Habit> list = service.viewHabits();
        if (list==null) {
            System.out.println("У вас ещё нет привычек!");
        } else {
            System.out.println("id | название | описание | частота");
            for (Habit element : list) {
                System.out.println(element);
            }
        }

    }


    private static void markHabit() {
        return;
    }

    private static void deleteHabit() {
        System.out.println("ID привычки, которую нужно удалить?");
        int id = console.nextInt();
        service.deleteHabit(id);
    }
}


