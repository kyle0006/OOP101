public class Student {
    private final String studentId;
    private String studentName;
    public Student(
    String studentId,
    String studentName) {
        if (studentId == null
        || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException(
            "Student ID cannot be blank."
            );
        }
        if (studentName == null
        || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException(
            "Student name cannot be blank."
            );
        }
        this.studentId = studentId;
        this.studentName = studentName;
    }
    public String getStudentId() {
        return studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void changeName(String studentName) {
        if (studentName == null
        || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException(
            "Student name cannot be blank."
            );
        }
        this.studentName = studentName;
    }
    @Override
    public String toString() {
        return studentId
        + " - "
        + studentName;
    }
}
