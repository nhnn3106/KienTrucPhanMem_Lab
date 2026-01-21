package strategy;

public class ProcessingFeeDecorator extends PaymentDecorator {
    private double feeRate;

    public ProcessingFeeDecorator(PaymentStrategy paymentStrategy, double feeRate) {
        super(paymentStrategy);
        this.feeRate = feeRate;
    }

    @Override
    public void pay(double amount) {
        double fee = amount * feeRate;
        double totalAmount = amount + fee;
        System.out.println("--- Áp dụng Phí xử lý (" + (feeRate * 100) + "%) ---");
        System.out.println("  Số tiền gốc: " + amount + " VND");
        System.out.println("  Phí xử lý: " + fee + " VND");
        System.out.println("  Tổng thanh toán: " + totalAmount + " VND");
        paymentStrategy.pay(totalAmount);
    }

    @Override
    public String getPaymentMethodName() {
        return paymentStrategy.getPaymentMethodName() + " (có phí xử lý)";
    }
}

