package org.example.homework45.controller;

import org.example.homework45.model.BulkAddResponse;
import org.example.homework45.model.DeleteResponse;
import org.example.homework45.model.Teacher;
import org.example.homework45.model.TeacherDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final List<Teacher> teacherList;

    public TeacherController() {
        this.teacherList = new ArrayList<>();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getTeachers() {
        if (this.teacherList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(teacherList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable int id) {
        if (id < 0 || id >= this.teacherList.size()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Teacher teacher = this.teacherList.get(id);
        if (teacher == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam(name = "name") String name) {
        List<Teacher> results = teacherList.stream()
                .filter(teacher -> teacher.getFirstName().equalsIgnoreCase(name))
                .toList();
        if (name.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }

    @GetMapping("/subject/{subject}")
    public ResponseEntity<?> getBySubject(@PathVariable String subject) {
        if (this.teacherList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (subject.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<Teacher> results = teacherList.stream()
                    .filter(teacher -> teacher.getSubject().equalsIgnoreCase(subject))
                    .toList();
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
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
        List<ResponseEntity<?>> errors = new ArrayList<>();
        return new BulkAddResponse();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Teacher> fullTeacherUpdate(@RequestBody TeacherDTO teacherDTO, @PathVariable Integer id) {
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {
                teacher.setFirstName(teacherDTO.getFirstName());
                teacher.setLastName(teacherDTO.getLastName());
                teacher.setEmail(teacherDTO.getEmail());
                teacher.setExperience(teacherDTO.getExperience());
                teacher.setSalary(teacherDTO.getSalary());
                teacher.setActive(teacherDTO.isActive());
                teacher.setSubject(teacherDTO.getSubject());
                return ResponseEntity.ok(teacher);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/update-partial/{id}")
    public ResponseEntity<Teacher> partialTeacherUpdate(@RequestBody TeacherDTO teacherDTO, @PathVariable Integer id) {
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {

                return ResponseEntity.ok(teacher);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Teacher> deactivate(@PathVariable Integer id) {
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {

                return ResponseEntity.ok(teacher);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Teacher> activate(@PathVariable Integer id) {
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {

                return ResponseEntity.ok(teacher);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/increase-salary/{id}")
    public ResponseEntity<Teacher> increaseSalary(@RequestParam Integer percent, @PathVariable Integer id) {
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {

                return ResponseEntity.ok(teacher);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-by-subject/{subject}")
    public ResponseEntity<DeleteResponse> deleteBySubject(@PathVariable String subject) {
        DeleteResponse deleteResponse = new DeleteResponse();
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getSubject(), subject)) {
                teacherList.remove(teacher);
                deleteResponse.deleted += 1;
            }
        }
        return ResponseEntity.ok(deleteResponse);
    }

    @DeleteMapping("/delete-inactive/")
    public ResponseEntity<DeleteResponse> deleteInactive() {
        DeleteResponse deleteResponse = new DeleteResponse();
        for (Teacher teacher : teacherList) {
            if (!teacher.isActive()) {
                teacherList.remove(teacher);
                deleteResponse.deleted += 1;
            }
        }
        return ResponseEntity.ok(deleteResponse);
    }
}
