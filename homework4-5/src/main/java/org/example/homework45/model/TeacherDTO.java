package org.example.homework45.model;

public class TeacherDTO {
    private final String firstName;
    private final String lastName;
    private final String subject;
    private final Integer experience;
    private final Double salary;
    private final String email;
    private final boolean isActive;

    public TeacherDTO(String firstName, String lastName, String subject, Integer experience, Double salary, String email, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
        this.experience = experience;
        this.salary = salary;
        this.email = email;
        this.isActive = isActive;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getExperience() {
        return experience;
    }

    public Double getSalary() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }
}
