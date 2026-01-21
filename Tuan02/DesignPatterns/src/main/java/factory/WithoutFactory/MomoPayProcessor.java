package factory.WithoutFactory;

public class MomoPayProcessor {
    private String phoneNumber;
    private double amount;

    public void processPayment(double amount, String phoneNumber) {
        this.amount = amount;
        this.phoneNumber = phoneNumber;
        validatePhone();
        deductBalance();
    }

    private void validatePhone() {
    }

    private void deductBalance() {
    }

    public double getAmount() {
        return amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
