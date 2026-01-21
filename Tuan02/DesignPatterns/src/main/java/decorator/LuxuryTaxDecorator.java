package decorator;

public class LuxuryTaxDecorator extends TaxDecorator {
    private static final double LUXURY_TAX_RATE = 0.15;

    public LuxuryTaxDecorator(Product product) {
        super(product);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " + Thuế xa xỉ (15%)";
    }

    @Override
    public double getPrice() {
        return product.getPrice() * (1 + LUXURY_TAX_RATE);
    }
}

