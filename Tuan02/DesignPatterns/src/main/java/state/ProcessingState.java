package state;

public class ProcessingState extends OrderState {

    @Override
    public void process() {
        System.out.println("❌ Đơn hàng đã ở trạng thái Đang xử lý\n");
    }

    @Override
    public void ship() {
        System.out.println("[Đang xử lý] Đóng gói và vận chuyển đơn hàng #" + order.getOrderId());
        System.out.println("  - Đóng gói sản phẩm");
        System.out.println("  - In phiếu vận chuyển");
        System.out.println("  - Bàn giao cho đơn vị vận chuyển");
        System.out.println("✅ Chuyển sang trạng thái: Đã giao\n");
        order.setState(new DeliveredState());
    }

    @Override
    public void deliver() {
        System.out.println("❌ Phải vận chuyển trước khi giao hàng\n");
    }

    @Override
    public void cancel() {
        System.out.println("[Đang xử lý] Hủy đơn hàng #" + order.getOrderId());
        System.out.println("  - Dừng xử lý đơn hàng");
        System.out.println("  - Hoàn tiền: " + order.getAmount() + " VND");
        System.out.println("✅ Đơn hàng đã được hủy\n");
        order.setState(new CancelledState());
    }
}

