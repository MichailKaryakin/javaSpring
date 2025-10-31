package org.example.homework45.model;

public class Teacher {
    private static Integer idCount = 0;
    private final Integer id;
    private String firstName;
    private String lastName;
    private String subject;
    private Integer experience;
    private Double salary;
    private String email;
    private boolean isActive;

    public Teacher(TeacherDTO teacherDTO) {
        this.id = idCount++;
        this.firstName = teacherDTO.getFirstName();
        this.lastName = teacherDTO.getLastName();
        this.subject = teacherDTO.getSubject();
        this.experience = teacherDTO.getExperience();
        this.salary = teacherDTO.getSalary();
        this.email = teacherDTO.getEmail();
        this.isActive = teacherDTO.isActive();
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
