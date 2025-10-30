package model;

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

    private static String getProperInsert(Student student) {
        String sqlQuery = "INSERT INTO Students (Id, Name, Surname, Age, Email) VALUES ("
                + student.getId() + ", '"
                + student.getName() + "', '" + student.getSurname() + "', '"
                + student.getAge() + "', '" + student.getEmail() + "')";
        if (student.getPhone() != null) {
            sqlQuery = "INSERT INTO Students (Id, Name, Surname, Age, Email, Phone) VALUES ("
                    + student.getId() + ", '" + student.getName()
                    + "', '" + student.getSurname() + "', '" + student.getAge()
                    + "', '" + student.getEmail() + "', '" + student.getPhone() + "')";
        }
        return sqlQuery;
    }

    private List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            String sqlQuery = "SELECT * FROM Students";

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
            System.out.println(e.getMessage());
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
            if (student.getAge() >= age) {
                result.append(student).append('\n');
            }
        }
        return result.toString();
    }

    public String addStudent(Student student) {
        students.add(student);
        try {
            String sqlQuery = getProperInsert(student);

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                resultSet.next();
                students.add(student);
                return "Студент добавлен";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteStudent(int id) {
        try {
            String sqlQuery = "DELETE FROM Students WHERE Id = " + id;

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                resultSet.next();
                students.removeIf(student -> student.getId() == id);
                return "Студент удалён";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
