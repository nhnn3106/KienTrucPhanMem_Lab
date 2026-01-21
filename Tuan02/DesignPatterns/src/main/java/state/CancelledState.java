package state;

public class CancelledState extends OrderState {

    @Override
    public void process() {
        System.out.println("❌ Đơn hàng đã bị hủy, không thể xử lý\n");
    }

    @Override
    public void ship() {
        System.out.println("❌ Đơn hàng đã bị hủy, không thể vận chuyển\n");
    }

    @Override
    public void deliver() {
        System.out.println("❌ Đơn hàng đã bị hủy, không thể giao hàng\n");
    }

    @Override
    public void cancel() {
        System.out.println("❌ Đơn hàng đã ở trạng thái Đã hủy\n");
    }
}

