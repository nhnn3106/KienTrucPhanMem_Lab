package strategy;

public class CreditCardStrategy implements PaymentStrategy {
    private String cardNumber;
    private String cardHolder;

    public CreditCardStrategy(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán " + amount + " VND bằng Thẻ tín dụng");
        System.out.println("  Chủ thẻ: " + cardHolder);
        System.out.println("  Số thẻ: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
    }

    @Override
    public String getPaymentMethodName() {
        return "Thẻ tín dụng";
    }
}

