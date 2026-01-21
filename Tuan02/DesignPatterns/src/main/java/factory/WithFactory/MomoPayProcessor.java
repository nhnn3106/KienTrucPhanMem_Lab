package factory.WithFactory;

public class MomoPayProcessor extends PaymentProcessor {
    private String phoneNumber;

    @Override
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

