package factory.WithFactory;

public class CreditCardProcessor extends PaymentProcessor {
    private String cardNumber;

    @Override
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

    public String getCardNumber() {
        return cardNumber;
    }
}

