package strategy;

public abstract class PaymentDecorator implements PaymentStrategy {
    protected PaymentStrategy paymentStrategy;

    public PaymentDecorator(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    @Override
    public void pay(double amount) {
        paymentStrategy.pay(amount);
    }

    @Override
    public String getPaymentMethodName() {
        return paymentStrategy.getPaymentMethodName();
    }
}

