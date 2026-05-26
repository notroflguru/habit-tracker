import java.util.*;

public class Main {
    private static Scanner console = new Scanner(System.in);
    public static void main(String[] args) {
        UserRepository userRepo = new JdbcUserRepository(
                "jdbc:postgresql://localhost:5432/habit_tracker",
                "postgres",
                "secret");
        AuthService authService = new AuthService(userRepo);
        User currentUser = null;
        while (currentUser==null) {
            currentUser = loginMenu(authService);
        }
        System.out.println("Вы успешно вошли, " + currentUser.getLogin());
        HabitRepository habitRepo = new JdbcHabitRepository(
                "jdbc:postgresql://localhost:5432/habit_tracker",
                "postgres",
                "secret",
                currentUser.getUserId()
        );

        Service service = new Service(currentUser, habitRepo);

        System.out.println("=== Трекер привычек ===");

        while (true) {
            mainMenu(service);
        }

    }


    private static User loginMenu(AuthService authService) {
        System.out.println("1. Вход");
        System.out.println("2. Регистрация");
        System.out.println("0. Выход");

        Byte command = console.nextByte();
        console.nextLine();
        switch (command) {
            default: System.out.println("Команда не распознана!"); return null;
            case 0: System.exit(0);
            case 1: return userLogin(authService);
            case 2: return userRegister(authService);
        }
    }


    private static User userLogin(AuthService authService) {
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


    private static User userRegister(AuthService authService) {
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


    private static void mainMenu(Service service) {
        System.out.println("1. Создать привычку");
        System.out.println("2. Посмотреть привычки");
        System.out.println("3. Удалить привычку");
        System.out.println("0. Выход");

        Byte command = console.nextByte();
        console.nextLine();
        switch (command) {
            default: System.out.println("Команда не распознана!"); return;
            case 0: System.exit(0);
            case 1: createHabit(service); break;
            case 2: viewHabits(service); break;
            case 3: deleteHabit(service); break;
        }
    }


    private static void createHabit(Service service) {
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


    private static void viewHabits(Service service) {
        ArrayList<Habit> habits = service.viewHabits();
        if (habits==null) {
            System.out.println("У вас ещё нет привычек!");
        } else {
            System.out.printf("%-4s | %-20s | %-30s | %-12s%n", "id", "название", "описание", "частота");
            System.out.println("=".repeat(75));
            for (Habit h : habits) {
                System.out.printf("%-4d | %-20s | %-30s | %-12s%n", h.getId(), h.getHabitName(), h.getDescription(), h.getFrequency());
            }
            System.out.println("=".repeat(75));
        }

    }


    private static void deleteHabit(Service service) {
        System.out.println("ID привычки, которую нужно удалить?");
        int id = console.nextInt();
        service.deleteHabit(id);
    }
}


