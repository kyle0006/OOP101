package campusconnect.service;
import campusconnect.exception.*;
import campusconnect.model.*;
import java.util.*;
import java.util.stream.*;
public final class CampusConnectService implements Repository<ServiceRequest,String> {
    private final Map<String,ServiceRequest> requestsById=new LinkedHashMap<>();
    private final Queue<ServiceRequest> pending=new ArrayDeque<>();
    private final Set<String> studentIds=new HashSet<>();
    private final List<ServiceRequest> history=new ArrayList<>();
    public void add(ServiceRequest r)throws DuplicateTicketException,InvalidRequestException {
        if(r==null)throw new InvalidRequestException("Request cannot be null.");
        if(requestsById.containsKey(r.getTicketId()))throw new DuplicateTicketException("Ticket ID already exists: "+r.getTicketId());
        if(!studentIds.add(r.getStudent().getStudentId())) {
            studentIds.remove(r.getStudent().getStudentId());
            studentIds.add(r.getStudent().getStudentId());
        }
        requestsById.put(r.getTicketId(),r);
        pending.add(r);
        history.add(r);
    }
    public Optional<ServiceRequest> findByKey(String id) {
        return Optional.ofNullable(requestsById.get(id));
    }
    public List<ServiceRequest> listAll() {
        return List.copyOf(requestsById.values());
    }
    public List<ServiceRequest> search(String ticketId,String studentId,ServiceCategory category,RequestStatus status) {
        return requestsById.values().stream().filter(r->ticketId==null||ticketId.isBlank()||r.getTicketId().equalsIgnoreCase(ticketId)).filter(r->studentId==null||studentId.isBlank()||r.getStudent().getStudentId().equalsIgnoreCase(studentId)).filter(r->category==null||r.getCategory()==category).filter(r->status==null||r.getStatus()==status).sorted(Comparator.comparingInt(ServiceRequest::getPriority).reversed().thenComparingInt(ServiceRequest::estimateDays).thenComparing(ServiceRequest::getTicketId)).toList();
    }
    public void updateStatus(String id,RequestStatus next)throws InvalidRequestException,InvalidStatusTransitionException {
        ServiceRequest r=findByKey(id).orElseThrow(()->new InvalidRequestException("Ticket not found: "+id));
        RequestStatus old=r.getStatus();
        boolean allowed=switch(old) {
            case NEW->next==RequestStatus.IN_PROGRESS||next==RequestStatus.CANCELLED;
            case IN_PROGRESS->next==RequestStatus.RESOLVED||next==RequestStatus.CANCELLED;
            case RESOLVED,CANCELLED->false;
        }
        ;
        if(!allowed)throw new InvalidStatusTransitionException("Cannot change "+old+" to "+next+".");
        if(next!=RequestStatus.NEW)pending.remove(r);
        r.setStatus(next);
    }
    public ServiceRequest processNext()throws EmptyQueueException {
        while(!pending.isEmpty()) {
            ServiceRequest r=pending.remove();
            if(r.getStatus()==RequestStatus.NEW) {
                r.setStatus(RequestStatus.IN_PROGRESS);
                return r;
            }
        }
        throw new EmptyQueueException("No pending NEW requests.");
    }
    public void remove(String id)throws InvalidRequestException {
        ServiceRequest r=findByKey(id).orElseThrow(()->new InvalidRequestException("Ticket not found: "+id));
        requestsById.remove(id);
        pending.remove(r);
        history.remove(r);
        studentIds.remove(r.getStudent().getStudentId());
    }
    public RequestSummary summary() {
        Map<ServiceCategory,Integer> cats=Arrays.stream(ServiceCategory.values()).collect(Collectors.toMap(c->c,c->0,(a,b)->b,LinkedHashMap::new));
        Map<RequestStatus,Integer> stats=Arrays.stream(RequestStatus.values()).collect(Collectors.toMap(s->s,s->0,(a,b)->b,LinkedHashMap::new));
        int total=0;
        for(ServiceRequest r:history) {
            cats.compute(r.getCategory(),(k,v)->v+1);
            stats.compute(r.getStatus(),(k,v)->v+1);
            total+=r.estimateDays();
        }
        return new RequestSummary(cats,stats,history.isEmpty()?0.0:(double)total/history.size(),history.size());
    }
    public Map<String,Integer> collectionSizes() {
        return Map.of("map",requestsById.size(),"queue",pending.size(),"set",studentIds.size(),"history",history.size());
    }
}
