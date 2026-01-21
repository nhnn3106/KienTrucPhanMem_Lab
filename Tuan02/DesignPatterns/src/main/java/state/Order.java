package state;

public class Order {
    private String orderId;
    private double amount;
    private OrderState currentState;

    public Order(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.currentState = new NewOrderState();
        this.currentState.setOrder(this);
    }

    public void setState(OrderState state) {
        this.currentState = state;
        this.currentState.setOrder(this);
    }

    public void process() {
        currentState.process();
    }

    public void ship() {
        currentState.ship();
    }

    public void deliver() {
        currentState.deliver();
    }

    public void cancel() {
        currentState.cancel();
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrentStateName() {
        return currentState.getClass().getSimpleName();
    }
}

