package campusconnect.model;
public final class AdvisingRequest extends ServiceRequest {
    public AdvisingRequest(String id,Student s,int p,String d) {
        super(id,s,p,d);
    }
    public int estimateDays() {
        return getCategory().estimate(getPriority());
    }
    public String getRequestType() {
        return "ADVISING";
    }
}
