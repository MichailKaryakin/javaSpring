package org.example.homework3.controller;

import org.example.homework3.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    List<Student> students;

    public StudentController() {
        this.students = new ArrayList<>();
    }

    private boolean checkEmail(String email) {
        for (Student student : students) {
            if (Objects.equals(student.getEmail(), email)) {
                return false;
            }
        }
        return true;
    }

    @GetMapping("/all")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/get/{id}")
    public Student getStudent(@PathVariable int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    @GetMapping("/searchByName/{name}")
    public Student searchByName(@PathVariable String name) {
        for (Student student : students) {
            if (Objects.equals(student.getName(), name)) {
                return student;
            }
        }
        return null;
    }

    @GetMapping("/searchByMail/{email}")
    public Student searchByMail(@PathVariable String email) {
        for (Student student : students) {
            if (Objects.equals(student.getEmail(), email)) {
                return student;
            }
        }
        return null;
    }

    @GetMapping("/add")
    public String addStudent(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "surname") String surname,
            @RequestParam(name = "age") short age,
            @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(name = "email") String email
    ) {
        if (checkEmail(email)) {
            Student newStudent = new Student(name, surname, age, phoneNumber, email);
            students.add(newStudent);
            return "student added";
        }
        return "failed, student under such email or id already exists";
    }

    @GetMapping("/update/{id}")
    public String updateStudent(
            @PathVariable int id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "surname", required = false) String surname,
            @RequestParam(name = "age", required = false) short age,
            @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(name = "email", required = false) String email
    ) {
        for (Student student : students) {
            if (student.getId() == id) {
                if (name != null) {
                    student.setName(name);
                }
                if (surname != null) {
                    student.setSurname(surname);
                }
                if (age != 0) {
                    student.setAge(age);
                }
                if (phoneNumber != null) {
                    student.setPhoneNumber(phoneNumber);
                }
                if (email != null) {
                    student.setEmail(email);
                }
                return "student data updated";
            }
        }
        return "no student under transmitted id, nothing changed";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                students.remove(student);
                return "student successfully removed";
            }
        }
        return "no student under transmitted id, nothing changed";
    }

    @GetMapping("/flush")
    public String flushData() {
        students.clear();
        return "congrats, all the students have been erased!";
    }

    @GetMapping("/filter/{age}")
    public String filterByAge(@PathVariable short age) {
        students.removeIf(student -> student.getAge() < age);
        return "yes, sir, each and every one of them minors been filtered";
    }
}
