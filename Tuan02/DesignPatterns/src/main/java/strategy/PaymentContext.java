package strategy;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void executePayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("❌ Chưa chọn phương thức thanh toán!");
            return;
        }
        System.out.println("=== Bắt đầu thanh toán ===");
        System.out.println("Phương thức: " + paymentStrategy.getPaymentMethodName());
        paymentStrategy.pay(amount);
        System.out.println("✅ Giao dịch thành công!\n");
    }
}

