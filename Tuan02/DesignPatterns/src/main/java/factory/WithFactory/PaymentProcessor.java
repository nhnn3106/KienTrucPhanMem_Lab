package factory.WithFactory;

public abstract class PaymentProcessor {
    protected double amount;

    public abstract void processPayment(double amount, String credential);

    public double getAmount() {
        return amount;
    }
}
