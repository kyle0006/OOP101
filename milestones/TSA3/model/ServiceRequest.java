package campusconnect.model;
public abstract class ServiceRequest implements Prioritizable {
    public static final int MIN_PRIORITY=1,MAX_PRIORITY=5,MIN_ESTIMATE=1;
    private final String ticketId;
    private final Student student;
    private final String description;
    private final int priority;
    private RequestStatus status;
    protected ServiceRequest(String id,Student student,int priority,String desc) {
        if(id==null||id.isBlank())throw new IllegalArgumentException("Ticket ID cannot be blank.");
        if(student==null)throw new IllegalArgumentException("Student cannot be null.");
        if(priority<MIN_PRIORITY||priority>MAX_PRIORITY)throw new IllegalArgumentException("Priority must be between 1 and 5.");
        if(desc==null||desc.isBlank())throw new IllegalArgumentException("Concern description cannot be blank.");
        ticketId=id.trim();
        this.student=student;
        this.priority=priority;
        description=desc.trim();
        status=RequestStatus.NEW;
    }
    public String getTicketId() {
        return ticketId;
    }
    public Student getStudent() {
        return student;
    }
    public String getDescription() {
        return description;
    }
    public int getPriority() {
        return priority;
    }
    public boolean isHighPriority() {
        return priority>=4;
    }
    public RequestStatus getStatus() {
        return status;
    }
    public void setStatus(RequestStatus s) {
        status=s;
    }
    public abstract int estimateDays();
    public ServiceCategory getCategory() {
        return ServiceCategory.valueOf(getRequestType());
    }
    public abstract String getRequestType();
    @Override public String toString() {
        return ticketId+" | "+student.getStudentId()+" | "+getRequestType()+" | P"+priority+" | "+estimateDays()+" day(s) | "+status;
    }
}
