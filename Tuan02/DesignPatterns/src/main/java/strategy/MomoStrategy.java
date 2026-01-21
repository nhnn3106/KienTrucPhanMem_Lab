package strategy;

public class MomoStrategy implements PaymentStrategy {
    private String phoneNumber;

    public MomoStrategy(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán " + amount + " VND bằng Ví Momo");
        System.out.println("  Số điện thoại: " + phoneNumber);
    }

    @Override
    public String getPaymentMethodName() {
        return "Ví Momo";
    }
}

