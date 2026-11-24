package campusconnect;
import campusconnect.exception.*;
import campusconnect.model.*;
import campusconnect.service.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CampusConnectServiceTest {
    private CampusConnectService service() {
        CampusConnectService s=new CampusConnectService();
        try {
            s.add(new EnrollmentRequest("T-002",new Student("S2","Ben"),3,"enroll"));
            s.add(new AdvisingRequest("T-001",new Student("S1","Ana"),5,"advice"));
            s.add(new DocumentRequest("T-003",new Student("S3","Cara"),1,"document"));
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        return s;
    }
    @Test void duplicateTicketIsRejectedAndMapUnchanged()throws Exception {
        CampusConnectService s=service();
        int before=s.listAll().size();
        assertThrows(DuplicateTicketException.class,()->s.add(new DocumentRequest("T-001",new Student("S9","Dio"),1,"x")));
        assertEquals(before,s.listAll().size());
    }
    @Test void invalidTransitionDoesNotChangeStatus()throws Exception {
        CampusConnectService s=service();
        assertThrows(InvalidStatusTransitionException.class,()->s.updateStatus("T-001",RequestStatus.RESOLVED));
        assertEquals(RequestStatus.NEW,s.findByKey("T-001").orElseThrow().getStatus());
    }
    @Test void emptyQueueRaisesSpecificException()throws Exception {
        CampusConnectService s=service();
        s.processNext();
        s.processNext();
        s.processNext();
        assertThrows(EmptyQueueException.class,s::processNext);
    }
    @Test void missingSearchIsEmpty() {
        assertTrue(service().findByKey("missing").isEmpty());
    }
    @Test void priorityThenEstimateThenIdSort() {
        var x=service().search("","",null,null);
        assertEquals("T-001",x.get(0).getTicketId());
        assertEquals("T-002",x.get(1).getTicketId());
    }
    @Test void categoryAndStatusFiltersWork()throws Exception {
        CampusConnectService s=service();
        s.updateStatus("T-001",RequestStatus.IN_PROGRESS);
        assertEquals(1,s.search("","",ServiceCategory.ADVISING,RequestStatus.IN_PROGRESS).size());
    }
    @Test void processNextMaintainsQueueAndMap() {
        CampusConnectService s=service();
        assertDoesNotThrow(s::processNext);
        assertEquals(3,s.listAll().size());
        assertEquals(2,s.collectionSizes().get("queue"));
    }
    @Test void aggregateCountsAreCorrect() {
        var r=service().summary();
        assertEquals(1,r.byCategory().get(ServiceCategory.ENROLLMENT));
        assertEquals(3,r.totalRequests());
        assertEquals(3,r.byStatus().get(RequestStatus.NEW));
    }
}
