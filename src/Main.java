import java.util.*;

public class Main {
    private static Scanner console = new Scanner(System.in);
    private static Service service;
    private static AuthService authService;
    public static void main(String[] args) {
        authService = new AuthService(new InMemoryUserRepository());
        User currentUser = null;
        while (currentUser==null) {
            currentUser = loginMenu();
        }
        service = new Service(currentUser, new InMemoryHabitRepository());

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
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
    }


    private static void viewHabits() {
        System.out.println(service.viewHabits());
    }


    private static void markHabit() {
        return;
    }

    private static void deleteHabit() {
        System.out.println("Название привычки, которую нужно удалить?");
        String name = console.nextLine();
        service.deleteHabit(name);
    }
}


