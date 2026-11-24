package campusconnect.model;
public enum ServiceCategory {
    ENROLLMENT(3), DOCUMENT(2), PAYMENT(1), ADVISING(4);
    private final int baseDays;
    ServiceCategory(int d) {
        baseDays=d;
    }
    public int estimate(int priority) {
        return Math.max(1,baseDays-(priority==5?1:0));
    }
}
