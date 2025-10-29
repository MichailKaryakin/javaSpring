public class Operation {
    private final int operationId;

    private final Database database = new Database();
    
    private final Console console = new Console();

    public Operation(int operationId) {
        this.operationId = operationId;
    }

    public String implement() {
        switch (operationId) {
            case 1 -> {
                return showStudents();
            }
            case 2 -> {
                return findStudent();
            }
            case 3 -> {
                return addStudent();
            }
            case 4 -> {
                return updateStudent();
            }
            case 5 -> {
                return deleteStudent();
            }
            case 6 -> {
                return findByName();
            }
            case 7 -> {
                return findByEmail();
            }
            case 8 -> {
                return ageFilter();
            }
            default -> {
                return "Что-то пошло не так";
            }
        }
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
        student.setAge(console.askAge());
        student.setName(console.askName());
        student.setSurname(console.askSurname());
        student.setEmail(console.askEmail());
        student.setPhone(console.askPhone());
        return "Студент успешно обновлён";
    }

    private String deleteStudent() {
        return "";
    }

    private String findByName() {
        return "";
    }

    private String findByEmail() {
        return "";
    }

    private String ageFilter() {
        return "";
    }
}
