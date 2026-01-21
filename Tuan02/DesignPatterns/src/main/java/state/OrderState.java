package state;

public abstract class OrderState {
    protected Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public abstract void process();
    public abstract void ship();
    public abstract void deliver();
    public abstract void cancel();
}

