package decorator;

public class Demo {
    public static void main(String[] args) {
        System.out.println("========== DECORATOR PATTERN - TÍNH THUẾ SẢN PHẨM ==========\n");

        System.out.println("--- Sản phẩm 1: Laptop (chỉ VAT) ---");
        Product laptop = new BasicProduct("Laptop Dell XPS", 20000000);
        System.out.println("Giá gốc: " + laptop.getDescription() + " = " + laptop.getPrice() + " VND");

        laptop = new VATDecorator(laptop);
        System.out.println("Giá sau thuế: " + laptop.getDescription() + " = " + laptop.getPrice() + " VND\n");

        System.out.println("--- Sản phẩm 2: Rượu vang (VAT + Thuế tiêu thụ) ---");
        Product wine = new BasicProduct("Rượu vang Pháp", 5000000);
        System.out.println("Giá gốc: " + wine.getDescription() + " = " + wine.getPrice() + " VND");

        wine = new VATDecorator(wine);
        wine = new ConsumptionTaxDecorator(wine);
        System.out.println("Giá sau thuế: " + wine.getDescription() + " = " + wine.getPrice() + " VND\n");

        System.out.println("--- Sản phẩm 3: Đồng hồ Rolex (VAT + Thuế xa xỉ) ---");
        Product watch = new BasicProduct("Đồng hồ Rolex", 500000000);
        System.out.println("Giá gốc: " + watch.getDescription() + " = " + watch.getPrice() + " VND");

        watch = new VATDecorator(watch);
        watch = new LuxuryTaxDecorator(watch);
        System.out.println("Giá sau thuế: " + watch.getDescription() + " = " + watch.getPrice() + " VND\n");

        System.out.println("--- Sản phẩm 4: Xe hơi sang (VAT + Thuế tiêu thụ + Thuế xa xỉ) ---");
        Product car = new BasicProduct("Mercedes S-Class", 3000000000.0);
        System.out.println("Giá gốc: " + car.getDescription() + " = " + car.getPrice() + " VND");

        car = new VATDecorator(car);
        car = new ConsumptionTaxDecorator(car);
        car = new LuxuryTaxDecorator(car);
        System.out.println("Giá sau thuế: " + car.getDescription() + " = " + car.getPrice() + " VND\n");
    }
}

