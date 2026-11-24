public abstract class ServiceRequest
implements Prioritizable {
    public static final int MIN_PRIORITY = 1;
    public static final int MAX_PRIORITY = 5;
    public static final int MIN_ESTIMATE = 1;
    private static int nextTicketNumber = 1;
    private final int ticketNumber;
    private final Student student;
    private final String description;
    private int priority;
    protected ServiceRequest(
    Student student,
    int priority,
    String description) {
        if (student == null) {
            throw new IllegalArgumentException(
            "Student cannot be null."
            );
        }
        if (priority < MIN_PRIORITY
        || priority > MAX_PRIORITY) {
            throw new IllegalArgumentException(
            "Priority must be between 1 and 5."
            );
        }
        if (description == null
        || description.trim().isEmpty()) {
            throw new IllegalArgumentException(
            "Concern description cannot be blank."
            );
        }
        this.ticketNumber = nextTicketNumber++;
        this.student = student;
        this.priority = priority;
        this.description = description;
    }
    public int getTicketNumber() {
        return ticketNumber;
    }
    public Student getStudent() {
        return student;
    }
    public String getDescription() {
        return description;
    }
    @Override
    public int getPriority() {
        return priority;
    }
    @Override
    public boolean isHighPriority() {
        return priority >= 4;
    }
    public void increasePriority() {
        if (priority < MAX_PRIORITY) {
            priority++;
        }
    }
    public abstract int estimateDays();
    public abstract String getRequestType();
    @Override
    public String toString() {
        return "Request Number: "
        + ticketNumber
        + ", Student ID: "
        + student.getStudentId()
        + ", Category: "
        + getRequestType()
        + ", Priority: "
        + priority
        + ", Estimated Days: "
        + estimateDays();
    }
}
