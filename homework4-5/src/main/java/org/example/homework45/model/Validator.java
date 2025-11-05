package org.example.homework45.model;

public class Validator {
    public static boolean nameIsNotValid(String name) {
        if (name == null) return true;
        return !name.matches("[a-zA-Z]+") || name.length() > 50 || name.length() < 2;
    }

    public static boolean emailIsNotValid(String email) {
        if (email == null) return true;
        return !email.matches("(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
    }

    public static boolean experienceIsNotValid(Integer experience) {
        return experience < 0 || experience > 50;
    }

    public static boolean salaryIsNotValid(Double salary) {
        return salary < 1 || salary > 100000;
    }

    public static boolean teacherIsNotValid(TeacherDTO teacherDTO) {
        return nameIsNotValid(teacherDTO.firstName())
                || nameIsNotValid(teacherDTO.lastName())
                || teacherDTO.subject().isEmpty()
                || salaryIsNotValid(teacherDTO.salary())
                || experienceIsNotValid(teacherDTO.experience())
                || emailIsNotValid(teacherDTO.email());
    }
}
