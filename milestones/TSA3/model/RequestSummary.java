package campusconnect.model;
import java.util.Map;
public record RequestSummary(Map<ServiceCategory,Integer> byCategory,Map<RequestStatus,Integer> byStatus,double averageEstimatedDays,int totalRequests) {
    public RequestSummary {
        byCategory=Map.copyOf(byCategory);
        byStatus=Map.copyOf(byStatus);
    }
}
