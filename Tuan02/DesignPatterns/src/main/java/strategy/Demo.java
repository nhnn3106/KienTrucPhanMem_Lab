package strategy;

public class Demo {
    public static void main(String[] args) {
        System.out.println("========== STRATEGY PATTERN - HỆ THỐNG THANH TOÁN ==========\n");

        PaymentContext paymentContext = new PaymentContext();

        System.out.println("--- Kịch bản 1: Thanh toán bằng Thẻ tín dụng ---");
        PaymentStrategy creditCard = new CreditCardStrategy("1234567890123456", "Nguyen Van A");
        paymentContext.setPaymentStrategy(creditCard);
        paymentContext.executePayment(1000000);

        System.out.println("--- Kịch bản 2: Thanh toán bằng PayPal ---");
        PaymentStrategy paypal = new PayPalStrategy("user@example.com");
        paymentContext.setPaymentStrategy(paypal);
        paymentContext.executePayment(500000);

        System.out.println("--- Kịch bản 3: Thanh toán bằng Momo ---");
        PaymentStrategy momo = new MomoStrategy("0901234567");
        paymentContext.setPaymentStrategy(momo);
        paymentContext.executePayment(750000);

        System.out.println("--- Kịch bản 4: Thẻ tín dụng + Phí xử lý 2% ---");
        PaymentStrategy creditCardWithFee = new ProcessingFeeDecorator(
            new CreditCardStrategy("1234567890123456", "Tran Thi B"),
            0.02
        );
        paymentContext.setPaymentStrategy(creditCardWithFee);
        paymentContext.executePayment(2000000);

        System.out.println("--- Kịch bản 5: PayPal + Mã giảm giá 10% ---");
        PaymentStrategy paypalWithDiscount = new DiscountDecorator(
            new PayPalStrategy("customer@gmail.com"),
            0.10
        );
        paymentContext.setPaymentStrategy(paypalWithDiscount);
        paymentContext.executePayment(1500000);

        System.out.println("--- Kịch bản 6: Momo + Giảm giá 15% + Phí xử lý 3% ---");
        PaymentStrategy momoWithDiscountAndFee = new ProcessingFeeDecorator(
            new DiscountDecorator(
                new MomoStrategy("0987654321"),
                0.15
            ),
            0.03
        );
        paymentContext.setPaymentStrategy(momoWithDiscountAndFee);
        paymentContext.executePayment(3000000);
    }

}
