package state;

public class NewOrderState extends OrderState {

    @Override
    public void process() {
        System.out.println("[Mới tạo] Kiểm tra thông tin đơn hàng #" + order.getOrderId());
        System.out.println("  - Xác nhận số tiền: " + order.getAmount() + " VND");
        System.out.println("  - Kiểm tra địa chỉ giao hàng");
        System.out.println("  - Xác nhận thanh toán");
        System.out.println("✅ Chuyển sang trạng thái: Đang xử lý\n");
        order.setState(new ProcessingState());
    }

    @Override
    public void ship() {
        System.out.println("❌ Không thể vận chuyển đơn hàng ở trạng thái Mới tạo\n");
    }

    @Override
    public void deliver() {
        System.out.println("❌ Không thể giao hàng ở trạng thái Mới tạo\n");
    }

    @Override
    public void cancel() {
        System.out.println("[Mới tạo] Hủy đơn hàng #" + order.getOrderId());
        System.out.println("  - Hoàn tiền: " + order.getAmount() + " VND");
        System.out.println("✅ Đơn hàng đã được hủy\n");
        order.setState(new CancelledState());
    }
}

