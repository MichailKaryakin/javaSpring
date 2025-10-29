public class Operation {
    private final int operationId;

    private final Database database = new Database();

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
        return "";
    }

    private String findStudent() {
        return "";
    }

    private String addStudent() {
        return "";
    }

    private String updateStudent() {
        return "";
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
