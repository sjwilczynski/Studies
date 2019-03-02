package list7.list7_1;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Database {
    private Map<UserType, List<User>> users;

    private Map<UserType, List<User>> getUsers() {
        return users;
    }

    private Database() {
        users = new HashMap<>();
        List<User> students = new ArrayList<>(Arrays.asList(new User("student1", LocalDate.of(1995, Month.MARCH, 16), 4, UserType.STUDENTS), new User("student2", LocalDate.of(1994, Month.APRIL, 13), 2, UserType.STUDENTS)));
        List<User> lectures = new ArrayList<>(Arrays.asList(new User("lecturer1", LocalDate.of(1975, Month.MAY, 10), 20, UserType.LECTURERS), new User("lecturer2", LocalDate.of(1981, Month.DECEMBER, 20), 10, UserType.LECTURERS)));
        List<User> others = new ArrayList<>(Arrays.asList(new User("other1", LocalDate.of(2000, Month.JANUARY, 1), 0, UserType.OTHER), new User("other2", LocalDate.of(1999, Month.NOVEMBER, 30), 1, UserType.STUDENTS)));
        users.put(UserType.STUDENTS, students);
        users.put(UserType.LECTURERS, lectures);
        users.put(UserType.OTHER, others);
    }

    private static Database instance;

    static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    public User getUser(UserType type, String name) {
        if (users.containsKey(type)) {
            for (User user : users.get(type)) {
                if (user.getName().equals(name)) {
                    return user;
                }
            }
        }
        throw new IllegalArgumentException("No user");
    }

    public List<User> getUsers(UserType type) {
        if (users.containsKey(type)) {
            return users.get(type);
        } else {
            return new ArrayList<>();
        }
    }

    public void addUser(User user) {
        if (!users.containsKey(user.getType())) {
            users.put(user.getType(), new ArrayList<>());
        }
        users.get(user.getType()).add(user);
    }
}


class User {
    private String name;
    private LocalDate birthDate;
    private int yearsInUni;
    private UserType type;


    User(String name, LocalDate birthDate, int yearsInUni, UserType type) {
        this.name = name;
        this.birthDate = birthDate;
        this.yearsInUni = yearsInUni;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getYearsInUni() {
        return yearsInUni;
    }

    public UserType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setYearsInUni(int yearsInUni) {
        this.yearsInUni = yearsInUni;
    }
}

enum UserType {
    STUDENTS("Students"), LECTURERS("Lecturers"), OTHER("Other");

    private final String text;

    UserType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static boolean contains(String text) {
        for (UserType type : UserType.values()) {
            if (type.toString().equals(text)) {
                return true;
            }
        }
        return false;
    }
}