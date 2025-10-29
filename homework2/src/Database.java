import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String url = "";
    private final String username = "";
    private final String password = "";
    private final List<Student> students;

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
        return "студент добавлен";
    }

    private static List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            String url = "jdbc:mysql://localhost:3306/Java521";
            String username = "root";
            String password = "root";

            String sqlQuery = "SELECT * FROM Students";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                if (resultSet.wasNull()) {
                    while (resultSet.next()) {
                        Student student = new Student(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("surname"),
                                resultSet.getInt("age"),
                                resultSet.getString("email")
                        );
                        student.setPhone(resultSet.getString("phone"));
                        students.add(student);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Bad connect");
        }

        return students;
    }
}
