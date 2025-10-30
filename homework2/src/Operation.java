public class Operation {
    private final int operationId;
    private final Database database;
    private final Console console;

    public Operation(int operationId, Database database) {
        this.operationId = operationId;
        this.database = database;
        this.console = new Console(database); // теперь корректно
    }

    public String implement() {
        return switch (operationId) {
            case 1 -> showStudents();
            case 2 -> findStudent();
            case 3 -> addStudent();
            case 4 -> updateStudent();
            case 5 -> deleteStudent();
            case 6 -> findByName();
            case 7 -> findByEmail();
            case 8 -> ageFilter();
            default -> "Что-то пошло не так";
        };
    }

    private String showStudents() {
        return database.getStudentsList();
    }

    private String findStudent() {
        int id = console.askId();
        Student student = database.studentById(id);
        if (student != null) {
            return student.toString();
        } else {
            return "Студента с таким идентификатором не найдено";
        }
    }

    private String addStudent() {
        Student student = new Student();
        student.setAge(console.askAge());
        student.setName(console.askName());
        student.setSurname(console.askSurname());
        student.setEmail(console.askEmail());
        student.setPhone(console.askPhone());
        return database.addStudent(student);
    }

    private String updateStudent() {
        int id = console.askId();
        Student student = database.studentById(id);
        if (student == null) {
            return "Студент с таким идентификатором не найден";
        }
        student.setAge(console.askAge());
        student.setName(console.askName());
        student.setSurname(console.askSurname());
        student.setEmail(console.askEmail());
        student.setPhone(console.askPhone());
        return "Студент успешно обновлён";
    }

    private String deleteStudent() {
        int id = console.askId();
        return database.deleteStudent(id);
    }

    private String findByName() {
        String name = console.askName();
        Student student = database.findByName(name);
        if (student == null) {
            return "Студента с таким именем не найдено";
        }
        return student.toString();
    }

    private String findByEmail() {
        String email = console.askEmail();
        Student student = database.findByEmail(email);
        if (student == null) {
            return "Студента с таким email не найдено";
        }
        return student.toString();
    }

    private String ageFilter() {
        int age = console.askAge();
        return database.ageFilter(age);
    }
}
