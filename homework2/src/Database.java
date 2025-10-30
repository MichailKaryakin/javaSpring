import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Database {
    private final String url = "jdbc:mysql://localhost:3306/Java";
    private final String username = "root";
    private final String password = "root";
    private final List<Student> students;

    private List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            String sqlQuery = "SELECT * FROM students";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {

                while (resultSet.next()) {
                    Student student = new Student(
                            resultSet.getInt("Id"),
                            resultSet.getString("Name"),
                            resultSet.getString("Surname"),
                            resultSet.getInt("Age"),
                            resultSet.getString("Email")
                    );
                    student.setPhone(resultSet.getString("Phone"));
                    students.add(student);
                }
            }
        } catch (Exception e) {
            System.out.println("Плохое соединение или ошибка селекта");
        }

        return students;
    }

    public Database() {
        this.students = getStudents();
    }

    public String getStudentsList() {
        StringBuilder info = new StringBuilder();
        for (Student student : students) {
            info.append(student);
            info.append('\n');
        }
        return info.toString();
    }

    public Student studentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public String addStudent(Student student) {
        students.add(student);
        return "Студент добавлен";
    }

    public String deleteStudent(int id) {
        if (students.removeIf(student -> student.getId() == id)) {
            return "Студент был удалён";
        } else {
            return "Студента с таким идентификатором не найдено";
        }
    }

    public Student findByName(String name) {
        for (Student student : students) {
            if (Objects.equals(student.getName(), name)) {
                return student;
            }
        }
        return null;
    }

    public Student findByEmail(String email) {
        for (Student student : students) {
            if (Objects.equals(student.getEmail(), email)) {
                return student;
            }
        }
        return null;
    }

    public String ageFilter(int age) {
        StringBuilder result = new StringBuilder();
        for (Student student : students) {
            if (student.getAge() == age) {
                result.append(student).append('\n');
            }
        }
        return result.toString();
    }
}
