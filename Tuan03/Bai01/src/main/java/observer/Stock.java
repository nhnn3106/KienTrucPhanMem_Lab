package observer;

public class Stock extends Subject {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
        notifyObservers("Cổ phiếu " + symbol + " vừa đổi giá thành: " + newPrice);
    }
}