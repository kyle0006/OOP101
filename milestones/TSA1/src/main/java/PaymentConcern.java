public class PaymentConcern
extends ServiceRequest {
    public PaymentConcern(
    Student student,
    int priority,
    String description) {
        super(
        student,
        priority,
        description
        );
    }
    @Override
    public int estimateDays() {
        int baseDays = 1;
        if (getPriority() == 5) {
            baseDays--;
        }
        if (baseDays < MIN_ESTIMATE) {
            baseDays = MIN_ESTIMATE;
        }
        return baseDays;
    }
    @Override
    public String getRequestType() {
        return "PAYMENT";
    }
}
