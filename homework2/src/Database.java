import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String url = "jdbc:mysql://localhost:3306/Java521";
    private final String username = "root";
    private final String password = "root";
    private List<Student> students;

    public Database() {
        this.students = getStudents();
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
