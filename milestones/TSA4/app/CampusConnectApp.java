package campusconnect.app;
import campusconnect.model.*;
import campusconnect.service.*;
import campusconnect.exception.*;
import java.util.*;
public final class CampusConnectApp {
    public static void main(String[]a)throws Exception {
        CampusConnectService s=new CampusConnectService();
        s.add(new EnrollmentRequest("CC-001",new Student("2026-001","Ana Cruz"),5,"Enrollment hold"));
        s.add(new DocumentRequest("CC-002",new Student("2026-002","Ben Santos"),3,"Request transcript"));
        s.add(new AdvisingRequest("CC-003",new Student("2026-003","Cara Reyes"),5,"Course advice"));
        System.out.println("SORTED REQUESTS");
        s.search("","",null,null).forEach(System.out::println);
        System.out.println("PROCESS NEXT: "+s.processNext());
        System.out.println("SUMMARY: "+s.summary());
        System.out.println("COLLECTION SIZES: "+s.collectionSizes());
    }
}
