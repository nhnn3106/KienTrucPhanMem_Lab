package strategy;

public class PayPalStrategy implements PaymentStrategy {
    private String email;

    public PayPalStrategy(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán " + amount + " VND bằng PayPal");
        System.out.println("  Email: " + email);
    }

    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }
}

