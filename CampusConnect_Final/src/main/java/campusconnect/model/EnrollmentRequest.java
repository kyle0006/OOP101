package campusconnect.model;
public final class EnrollmentRequest extends ServiceRequest {
    public EnrollmentRequest(String id,Student s,int p,String d) {
        super(id,s,p,d);
    }
    public int estimateDays() {
        return getCategory().estimate(getPriority());
    }
    public String getRequestType() {
        return "ENROLLMENT";
    }
}
