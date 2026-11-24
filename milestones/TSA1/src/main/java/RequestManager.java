public class RequestManager {
    private final ServiceRequest[] requests;
    private int requestCount;
    public RequestManager(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
            "Capacity must be greater than zero."
            );
        }
        requests =
        new ServiceRequest[capacity];
        requestCount = 0;
    }
    public void addRequest(
    ServiceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException(
            "Request cannot be null."
            );
        }
        if (requestCount >= requests.length) {
            throw new IllegalStateException(
            "Maximum number of requests reached."
            );
        }
        requests[requestCount] = request;
        requestCount++;
    }
    public ServiceRequest getRequest(
    int index) {
        if (index < 0
        || index >= requestCount) {
            throw new IndexOutOfBoundsException(
            "Invalid request index."
            );
        }
        return requests[index];
    }
    public int getRequestCount() {
        return requestCount;
    }
    public void printSummary() {
        if (requestCount == 0) {
            System.out.println(
            "No requests available."
            );
            return;
        }
        int enrollmentCount = 0;
        int documentCount = 0;
        int paymentCount = 0;
        int advisingCount = 0;
        int highestPriority =
        requests[0].getPriority();
        ServiceRequest highestPriorityRequest =
        requests[0];
        int totalEstimatedDays = 0;
        for (int i = 0;
        i < requestCount;
        i++) {
            ServiceRequest request =
            requests[i];
            switch (request.getRequestType()) {
                case "ENROLLMENT":
                enrollmentCount++;
                break;
                case "DOCUMENT":
                documentCount++;
                break;
                case "PAYMENT":
                paymentCount++;
                break;
                case "ADVISING":
                advisingCount++;
                break;
            }
            totalEstimatedDays +=
            request.estimateDays();
            if (request.getPriority()
            > highestPriority) {
                highestPriority =
                request.getPriority();
                highestPriorityRequest =
                request;
            }
        }
        double averageDays =
        (double) totalEstimatedDays
        / requestCount;
        System.out.println();
        System.out.println();
        System.out.println("FINAL SUMMARY");
        System.out.println();
        System.out.println(
        "ENROLLMENT: "
        + enrollmentCount
        );
        System.out.println(
        "DOCUMENT: "
        + documentCount
        );
        System.out.println(
        "PAYMENT: "
        + paymentCount
        );
        System.out.println(
        "ADVISING: "
        + advisingCount
        );
        System.out.println();
        System.out.println(
        "Highest Priority Request:"
        );
        System.out.println(
        "Student ID: "
        + highestPriorityRequest
        .getStudent()
        .getStudentId()
        );
        System.out.println(
        "Student Name: "
        + highestPriorityRequest
        .getStudent()
        .getStudentName()
        );
        System.out.println(
        "Priority: "
        + highestPriority
        );
        System.out.printf(
        "Average Estimated Days: %.2f%n",
        averageDays
        );
    }
}
