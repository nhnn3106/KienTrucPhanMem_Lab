package decorator;

public class ConsumptionTaxDecorator extends TaxDecorator {
    private static final double CONSUMPTION_TAX_RATE = 0.05;

    public ConsumptionTaxDecorator(Product product) {
        super(product);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " + Thuế tiêu thụ (5%)";
    }

    @Override
    public double getPrice() {
        return product.getPrice() * (1 + CONSUMPTION_TAX_RATE);
    }
}

