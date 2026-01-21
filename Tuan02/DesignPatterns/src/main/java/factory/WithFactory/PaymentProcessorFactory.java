package factory.WithFactory;

public class PaymentProcessorFactory {

    public static PaymentProcessor createProcessor(String type) {
        switch (type.toLowerCase()) {
            case "creditcard":
                return new CreditCardProcessor();
            case "momo":
                return new MomoPayProcessor();
            case "zalopay":
                return new ZaloPayProcessor();
            default:
                throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }
}

