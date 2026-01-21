package strategy;

public class DiscountDecorator extends PaymentDecorator {
    private double discountRate;

    public DiscountDecorator(PaymentStrategy paymentStrategy, double discountRate) {
        super(paymentStrategy);
        this.discountRate = discountRate;
    }

    @Override
    public void pay(double amount) {
        double discount = amount * discountRate;
        double finalAmount = amount - discount;
        System.out.println("--- Áp dụng Mã giảm giá (" + (discountRate * 100) + "%) ---");
        System.out.println("  Số tiền gốc: " + amount + " VND");
        System.out.println("  Giảm giá: -" + discount + " VND");
        System.out.println("  Số tiền sau giảm: " + finalAmount + " VND");
        paymentStrategy.pay(finalAmount);
    }

    @Override
    public String getPaymentMethodName() {
        return paymentStrategy.getPaymentMethodName() + " (có giảm giá)";
    }
}

