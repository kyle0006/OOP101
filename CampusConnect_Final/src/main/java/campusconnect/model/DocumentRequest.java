package campusconnect.model;
public final class DocumentRequest extends ServiceRequest {
    public DocumentRequest(String id,Student s,int p,String d) {
        super(id,s,p,d);
    }
    public int estimateDays() {
        return getCategory().estimate(getPriority());
    }
    public String getRequestType() {
        return "DOCUMENT";
    }
}
