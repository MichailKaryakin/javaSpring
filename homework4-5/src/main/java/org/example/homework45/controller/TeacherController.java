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
    public ResponseEntity<?> filterByExperienceAndSalary(@RequestParam(name = "minExp", required = false) Integer minExperience,
                                                         @RequestParam(name = "maxExp", required = false) Integer maxExperience,
                                                         @RequestParam(name = "minSal", required = false) Double minSalary,
                                                         @RequestParam(name = "maxSal", required = false) Double maxSalary) {
        List<Teacher> results = teacherList.stream()
                .filter(teacher -> teacher.getExperience() >= minExperience
                        && teacher.getExperience() <= maxExperience
                        && teacher.getSalary() >= minSalary
                        && teacher.getSalary() <= maxSalary)
                .toList();
        if (minSalary > maxSalary || minExperience > maxExperience) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActive() {
        List<Teacher> results = teacherList.stream()
                .filter(Teacher::isActive)
                .toList();
        if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        Integer result = teacherList.size();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/count-by-subject")
    public ResponseEntity<?> getCountBySubject() {
        Map<String, Integer> subjectsMap = new HashMap<>();
        teacherList.forEach(teacher -> subjectsMap.merge(teacher.getSubject(), 1, Integer::sum));
        if (subjectsMap.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(subjectsMap, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTeacher(@RequestBody TeacherDTO teacherDTO) {
        if (teacherDTO.firstName().isEmpty()) {  // валидация
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for (Teacher teacher : teacherList) {
            if (teacher.getFirstName().equalsIgnoreCase(teacherDTO.firstName())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        Teacher teacher = new Teacher(teacherDTO);
        teacherList.add(teacher);
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @PostMapping("/add-bulk")
    public ResponseEntity<?> addTeacher(@RequestBody List<TeacherDTO> teacherDTOList) {
        BulkAddResponse response = new BulkAddResponse();
        for (TeacherDTO teacherDTO : teacherDTOList) {
            if (teacherDTO.firstName().isEmpty()) { // валидация
                response.failed += 1;
                response.errors.add("Bad Request");
            }
            for (Teacher teacher : teacherList) {
                if (teacher.getFirstName().equalsIgnoreCase(teacherDTO.firstName())) {
                    response.failed += 1;
                    response.errors.add("Conflict");
                }
            }
            Teacher teacher = new Teacher(teacherDTO);
            teacherList.add(teacher);
            response.added += 1;
        }

        if (response.failed == 0) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (response.added > 0) {
            return new ResponseEntity<>(response.errors, HttpStatus.MULTI_STATUS);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> fullTeacherUpdate(@RequestBody TeacherDTO teacherDTO, @PathVariable Integer id) {
        if (teacherDTO.firstName().isEmpty()) { // валидация
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getFirstName(), teacherDTO.firstName())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {
                teacher.setFirstName(teacherDTO.firstName());
                teacher.setLastName(teacherDTO.lastName());
                teacher.setEmail(teacherDTO.email());
                teacher.setExperience(teacherDTO.experience());
                teacher.setSalary(teacherDTO.salary());
                teacher.setActive(teacherDTO.isActive());
                teacher.setSubject(teacherDTO.subject());
                return new ResponseEntity<>(teacher, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/update-partial/{id}")
    public ResponseEntity<?> partialTeacherUpdate(@RequestBody TeacherDTO teacherDTO, @PathVariable Integer id) {
        if (teacherDTO.firstName().isEmpty()) { // валидация
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            for (Teacher teacher : teacherList) {
                if (Objects.equals(teacher.getFirstName(), teacherDTO.firstName())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
        }
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {
                if (teacherDTO.firstName() != null) {
                    teacher.setFirstName(teacherDTO.firstName());
                }
                if (teacherDTO.lastName() != null) {
                    teacher.setLastName(teacherDTO.lastName());
                }
                if (teacherDTO.email() != null) {
                    teacher.setEmail(teacherDTO.email());
                }
                if (teacherDTO.experience() != null) {
                    teacher.setExperience(teacherDTO.experience());
                }
                if (teacherDTO.salary() != null) {
                    teacher.setSalary(teacherDTO.salary());
                }
                if (teacherDTO.isActive()) {
                    teacher.setActive(teacherDTO.isActive());
                }
                if (teacherDTO.subject() != null) {
                    teacher.setSubject(teacherDTO.subject());
                }
                return new ResponseEntity<>(teacher, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Integer id) {
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {
                if (teacher.isActive()) {
                    teacher.setActive(false);
                    return new ResponseEntity<>(teacher, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {
                if (!teacher.isActive()) {
                    teacher.setActive(true);
                    return new ResponseEntity<>(teacher, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/increase-salary/{id}")
    public ResponseEntity<?> increaseSalary(@RequestParam Integer percent, @PathVariable Integer id) {
        if (percent < 0 || percent > 100) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            for (Teacher teacher : teacherList) {
                if (Objects.equals(teacher.getId(), id)) {
                    double newSalary = teacher.getSalary() + (teacher.getSalary() * percent / 100);
                    if (newSalary > 10000) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    } else {
                        teacher.setSalary(newSalary);
                    }
                    return new ResponseEntity<>(teacher, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        if (id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getId(), id)) {
                teacherList.remove(teacher);
                return new ResponseEntity<>(teacher, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete-by-subject/{subject}")
    public ResponseEntity<?> deleteBySubject(@PathVariable String subject) {
        if (subject.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DeleteResponse deleteResponse = new DeleteResponse();
        for (Teacher teacher : teacherList) {
            if (Objects.equals(teacher.getSubject(), subject)) {
                teacherList.remove(teacher);
                deleteResponse.deleted += 1;
            }
        }
        if (deleteResponse.deleted == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete-inactive/")
    public ResponseEntity<?> deleteInactive() {
        DeleteResponse deleteResponse = new DeleteResponse();
        for (Teacher teacher : teacherList) {
            if (!teacher.isActive()) {
                teacherList.remove(teacher);
                deleteResponse.deleted += 1;
            }
        }
        if (deleteResponse.deleted == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
        }
    }
}
