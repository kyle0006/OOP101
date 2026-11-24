package campusconnect.model;
import java.util.Objects;
public final class Student {
    private final String studentId;
    private String studentName;
    public Student(String id,String name) {
        if(id==null||id.isBlank())throw new IllegalArgumentException("Student ID cannot be blank.");
        if(name==null||name.isBlank())throw new IllegalArgumentException("Student name cannot be blank.");
        studentId=id.trim();
        studentName=name.trim();
    }
    public String getStudentId() {
        return studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void changeName(String name) {
        if(name==null||name.isBlank())throw new IllegalArgumentException("Student name cannot be blank.");
        studentName=name.trim();
    }
    @Override public boolean equals(Object o) {
        return o instanceof Student s&&studentId.equals(s.studentId);
    }
    @Override public int hashCode() {
        return Objects.hash(studentId);
    }
    @Override public String toString() {
        return studentId+" - "+studentName;
    }
}
