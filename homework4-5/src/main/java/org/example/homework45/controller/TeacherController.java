package org.example.homework45.controller;

import org.example.homework45.model.BulkAddResponse;
import org.example.homework45.model.Teacher;
import org.example.homework45.model.TeacherDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final List<Teacher> teacherList;

    public TeacherController() {
        this.teacherList = new ArrayList<>();
    }

    @GetMapping("/all")
    public List<Teacher> getTeachers() {
        return teacherList;
    }

    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable int id) {
        return teacherList.get(id);
    }

    @GetMapping("/search")
    public List<Teacher> searchByName(@RequestParam(name = "name") String name) {
        return teacherList.stream()
                .filter(teacher -> teacher.getFirstName().equalsIgnoreCase(name))
                .toList();
    }

    @GetMapping("/subject/{subject}")
    public List<Teacher> getBySubject(@PathVariable String subject) {
        return teacherList.stream()
                .filter(teacher -> teacher.getSubject().equalsIgnoreCase(subject))
                .toList();
    }

    @GetMapping("/filter")
    public List<Teacher> filterByExperienceAndSalary(@RequestParam(name = "minExp", required = false) Integer minExperience,
                                                     @RequestParam(name = "maxExp", required = false) Integer maxExperience,
                                                     @RequestParam(name = "minSal", required = false) Double minSalary,
                                                     @RequestParam(name = "maxSal", required = false) Double maxSalary) {
        return teacherList.stream()
                .filter(teacher -> teacher.getExperience() >= minExperience
                        && teacher.getExperience() <= maxExperience
                        && teacher.getSalary() >= minSalary
                        && teacher.getSalary() <= maxSalary)
                .toList();
    }

    @GetMapping("/active")
    public List<Teacher> getActive() {
        return teacherList.stream()
                .filter(Teacher::isActive)
                .toList();
    }

    @GetMapping("/count")
    public Integer getCount() {
        return teacherList.size();
    }

    @GetMapping("/count-by-subject")
    public Map<String, Integer> getCountBySubject() {
        Map<String, Integer> subjectsMap = new HashMap<>();
        teacherList.forEach(teacher -> subjectsMap.merge(teacher.getSubject(), 1, Integer::sum));
        return subjectsMap;
    }

    @PostMapping("/add")
    public Teacher addTeacher(@RequestBody TeacherDTO teacherDTO) {
        Teacher newTeacher = new Teacher(teacherDTO);
        teacherList.add(newTeacher);
        return newTeacher;
    }

    @PostMapping("/add-bulk")
    public BulkAddResponse addTeacher(@RequestBody List<TeacherDTO> teacherDTOList) {
        return new BulkAddResponse();
    }
}
