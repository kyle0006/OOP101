package campusconnect.service;
import campusconnect.dao.CampusConnectDao;
import campusconnect.model.ServiceCategory;
import campusconnect.model.RequestStatus;
import campusconnect.exception.*;
public final class JdbcCampusConnectService {
    private final CampusConnectDao dao;
    public JdbcCampusConnectService(CampusConnectDao d) {
        dao=d;
    }
    private void text(String x,String l)throws InvalidRequestException {
        if(x==null||x.isBlank())throw new InvalidRequestException(l+" is required.");
    }
    public void add(String id,String sid,String name,ServiceCategory cat,int priority,String desc)throws Exception {
        text(id,"Ticket ID");
        text(sid,"Student ID");
        text(name,"Student name");
        text(desc,"Description");
        if(priority<1||priority>5)throw new InvalidRequestException("Priority must be 1-5.");
        try {
            dao.insert(id.trim(),sid.trim(),name.trim(),cat.name(),priority,desc.trim(),cat.estimate(priority));
        }
        catch(java.sql.SQLException e) {
            throw new DuplicateTicketException("Ticket already exists or could not be saved: "+e.getMessage());
        }
    }
    public void updateStatus(String id,RequestStatus status)throws Exception {
        var old=dao.findById(id).orElseThrow(()->new InvalidRequestException("Ticket not found."));
        boolean ok=switch(old.status()) {
            case "NEW"->status==RequestStatus.IN_PROGRESS||status==RequestStatus.CANCELLED;
            case "IN_PROGRESS"->status==RequestStatus.RESOLVED||status==RequestStatus.CANCELLED;
            default->false;
        }
        ;
        if(!ok)throw new InvalidStatusTransitionException("Invalid status transition.");
        dao.updateStatus(id,status.name());
    }
    public void delete(String id)throws Exception {
        dao.delete(id);
    }
    public CampusConnectDao dao() {
        return dao;
    }
}
