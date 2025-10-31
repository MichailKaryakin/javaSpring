package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Database {
    private final String url = "jdbc:mysql://localhost:3306/Java";
    private final String username = "root";
    private final String password = "root";
    private final List<Student> students;

    private int getMaxId() {
        try {
            String sqlQuery = "Select MAX(Id) from Students";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (Exception e) {
            System.out.println("Текущий максимальный идентификатор не получен");
            return -1;
        }
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
            System.out.println("Список студентов не считан из базы");
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
        try {
            String sqlQuery = "INSERT INTO Students (Id, Name, Surname, Age, Email, Phone) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

                preparedStatement.setInt(1, getMaxId() + 1);
                preparedStatement.setString(2, student.getName());
                preparedStatement.setString(3, student.getSurname());
                preparedStatement.setInt(4, student.getAge());
                preparedStatement.setString(5, student.getEmail());
                preparedStatement.setString(6, student.getPhone());
                preparedStatement.executeUpdate();

                students.clear();
                students.addAll(getStudents());
                return "Студент добавлен";
            }
        } catch (Exception e) {
            return "Студент не добавлен";
        }
    }

    public String deleteStudent(int id) {
        try {
            String sqlQuery = "DELETE FROM Students WHERE Id = " + id;

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate(sqlQuery);
                students.removeIf(student -> student.getId() == id);
                return "Студент удалён";
            }
        } catch (Exception e) {
            return "Студент не удалён";
        }
    }

    public String updateStudent(int id, Student student) {
        try {
            String sqlQuery = "UPDATE Students SET Name = ?, Surname = ?, Age = ?, Email = ?, Phone = ? WHERE Id = ?";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery) ) {
                preparedStatement.setString(1, student.getName());
                preparedStatement.setString(2, student.getSurname());
                preparedStatement.setInt(3, student.getAge());
                preparedStatement.setString(4, student.getEmail());
                preparedStatement.setString(5, student.getPhone());
                preparedStatement.setInt(6, id);

                preparedStatement.executeUpdate();
                students.clear();
                students.addAll(getStudents());
                return "Студент обновлён";
            }
        } catch (Exception e) {
            return "Студент не обновлён";
        }
    }
}
