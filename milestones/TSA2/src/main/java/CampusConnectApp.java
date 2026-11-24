import java.util.Scanner;
public class CampusConnectApp {
    public static final int MAX_REQUESTS = 5;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        RequestManager manager =
        new RequestManager(MAX_REQUESTS);
        for (int i = 0; i < MAX_REQUESTS; i++) {
            System.out.println();
            System.out.println("Request " + (i + 1));
            System.out.println();
            String studentId =
            getRequiredText(
            input,
            "Student ID: "
            );
            String studentName =
            getRequiredText(
            input,
            "Student Name: "
            );
            Student student =
            new Student(
            studentId,
            studentName
            );
            String category =
            getCategory(input);
            int priority =
            getPriority(input);
            String concern =
            getRequiredText(
            input,
            "Concern Description: "
            );
            ServiceRequest request =
            createRequest(
            student,
            category,
            priority,
            concern
            );
            manager.addRequest(request);
            printTicket(request);
        }
        manager.printSummary();
        System.out.println();
        System.out.println("POLYMORPHISM TEST");
        System.out.println();
        for (int i = 0; i < manager.getRequestCount(); i++) {
            ServiceRequest request =
            manager.getRequest(i);
            System.out.println(
            request.getRequestType()
            + " -> "
            + request.estimateDays()
            + " working day(s)"
            );
        }
        System.out.println();
        System.out.println("POLYMORPHISM TEST 2");
        System.out.println();
        for (int i = 0; i < manager.getRequestCount(); i++) {
            ServiceRequest request =
            manager.getRequest(i);
            System.out.println(
            "Object Type: "
            + request.getClass().getSimpleName()
            + " -> "
            + request.getRequestType()
            );
        }
        input.close();
    }
    public static String getRequiredText(
    Scanner input,
    String prompt) {
        while (true) {
            System.out.print(prompt);
            String value =
            input.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println(
            "Invalid input. This field cannot be blank."
            );
        }
    }
    public static String getCategory(
    Scanner input) {
        while (true) {
            System.out.print(
            "Service Category (ENROLLMENT/DOCUMENT/PAYMENT/ADVISING): "
            );
            String category =
            input.nextLine()
            .trim()
            .toUpperCase();
            if (validateCategory(category)) {
                return category;
            }
            System.out.println(
            "Invalid service category."
            );
        }
    }
    public static boolean validateCategory(
    String category) {
        return category.equals("ENROLLMENT")
        || category.equals("DOCUMENT")
        || category.equals("PAYMENT")
        || category.equals("ADVISING");
    }
    public static int getPriority(
    Scanner input) {
        while (true) {
            System.out.print("Priority (1-5): ");
            String value =
            input.nextLine().trim();
            try {
                int priority =
                Integer.parseInt(value);
                if (priority >= ServiceRequest.MIN_PRIORITY
                && priority <= ServiceRequest.MAX_PRIORITY) {
                    return priority;
                }
                System.out.println(
                "Invalid priority. Enter a value from 1 to 5."
                );
            }
            catch (NumberFormatException e) {
                System.out.println(
                "Invalid input. Enter a whole number."
                );
            }
        }
    }
    public static ServiceRequest createRequest(
    Student student,
    String category,
    int priority,
    String concern) {
        switch (category) {
            case "ENROLLMENT":
            return new EnrollmentRequest(
            student,
            priority,
            concern
            );
            case "DOCUMENT":
            return new DocumentRequest(
            student,
            priority,
            concern
            );
            case "PAYMENT":
            return new PaymentConcern(
            student,
            priority,
            concern
            );
            case "ADVISING":
            return new AdvisingRequest(
            student,
            priority,
            concern
            );
            default:
            throw new IllegalArgumentException(
            "Invalid category."
            );
        }
    }
    public static void printTicket(
    ServiceRequest request) {
        System.out.println();
        System.out.println("REQUEST TICKET");
        System.out.println();
        System.out.println(
        "Request Number: "
        + request.getTicketNumber()
        );
        System.out.println(
        "Student ID: "
        + request.getStudent().getStudentId()
        );
        System.out.println(
        "Student Name: "
        + request.getStudent().getStudentName()
        );
        System.out.println(
        "Service Category: "
        + request.getRequestType()
        );
        System.out.println(
        "Priority: "
        + request.getPriority()
        );
        System.out.println(
        "Concern: "
        + request.getDescription()
        );
        System.out.println(
        "Estimated Completion: "
        + request.estimateDays()
        + " working day(s)"
        );
        System.out.println("------------------------------------");
    }
}
