package state;

public class Demo {
    public static void main(String[] args) {
        System.out.println("========== STATE PATTERN DEMO ==========\n");

        System.out.println("--- Kịch bản 1: Luồng đơn hàng thành công ---");
        Order order1 = new Order("ORD001", 500000);
        order1.process();
        order1.ship();
        order1.deliver();

        System.out.println("\n--- Kịch bản 2: Hủy đơn hàng khi đang xử lý ---");
        Order order2 = new Order("ORD002", 750000);
        order2.process();
        order2.cancel();
        order2.ship();

        System.out.println("\n--- Kịch bản 3: Hủy đơn hàng mới ---");
        Order order3 = new Order("ORD003", 1200000);
        order3.cancel();

        System.out.println("\n--- Kịch bản 4: Thử các hành động không hợp lệ ---");
        Order order4 = new Order("ORD004", 300000);
        order4.ship();
        order4.deliver();
        order4.process();
    }
}

