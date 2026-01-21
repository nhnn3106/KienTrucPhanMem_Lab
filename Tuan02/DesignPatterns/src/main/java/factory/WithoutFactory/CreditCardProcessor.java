package factory.WithoutFactory;

public class CreditCardProcessor {
    private String cardNumber;
    private double amount;

    public void processPayment(double amount, String cardNumber) {
        this.amount = amount;
        this.cardNumber = cardNumber;
        validateCard();
        chargeCard();
    }

    private void validateCard() {
        this.cardNumber = maskCardNumber(cardNumber);
    }

    private void chargeCard() {
    }

    private String maskCardNumber(String card) {
        return "**** **** **** " + card.substring(card.length() - 4);
    }

    public double getAmount() {
        return amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
