package state;

public class DeliveredState extends OrderState {

    @Override
    public void process() {
        System.out.println("❌ Đơn hàng đã được giao, không thể xử lý lại\n");
    }

    @Override
    public void ship() {
        System.out.println("❌ Đơn hàng đã được giao, không thể vận chuyển lại\n");
    }

    @Override
    public void deliver() {
        System.out.println("❌ Đơn hàng đã ở trạng thái Đã giao\n");
    }

    @Override
    public void cancel() {
        System.out.println("❌ Không thể hủy đơn hàng đã được giao\n");
    }
}

