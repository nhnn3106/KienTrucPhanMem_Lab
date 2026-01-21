package factory;

import factory.WithFactory.PaymentProcessor;
import factory.WithFactory.PaymentProcessorFactory;

public class Demo {

    public static void main(String[] args) {
        demoWithoutFactory();
        demoWithFactory();
    }

    private static void demoWithoutFactory() {
        String paymentType = "momo";
        double amount = 100000;
        String credential = "0912345678";

        if (paymentType.equals("creditcard")) {
            factory.WithoutFactory.CreditCardProcessor processor =
                new factory.WithoutFactory.CreditCardProcessor();
            processor.processPayment(amount, credential);
            double processedAmount = processor.getAmount();
            System.out.println("Without Factory - Processed Amount: " + processedAmount);
        } else if (paymentType.equals("momo")) {
            factory.WithoutFactory.MomoPayProcessor processor =
                new factory.WithoutFactory.MomoPayProcessor();
            processor.processPayment(amount, credential);
            double processedAmount = processor.getAmount();
            System.out.println("Without Factory - Processed Amount: " + processedAmount);
        } else if (paymentType.equals("zalopay")) {
            factory.WithoutFactory.ZaloPayProcessor processor =
                new factory.WithoutFactory.ZaloPayProcessor();
            processor.processPayment(amount, credential);
            double processedAmount = processor.getAmount();
            System.out.println("Without Factory - Processed Amount: " + processedAmount);
        }
    }

    private static void demoWithFactory() {
        String paymentType = "momo";
        double amount = 100000;
        String credential = "0912345678";

        PaymentProcessor processor = PaymentProcessorFactory.createProcessor(paymentType);
        processor.processPayment(amount, credential);
        double processedAmount = processor.getAmount();
        System.out.println("With Factory - Processed Amount: " + processedAmount);
    }
}

