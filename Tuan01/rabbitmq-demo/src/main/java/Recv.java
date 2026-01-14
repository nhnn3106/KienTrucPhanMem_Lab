import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {
    private final static String QUEUE_NAME = "java_queue";

    public static void main(String[] argv) throws Exception {
        // 1. Thiết lập kết nối tương tự Producer
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 2. Khai báo hàng đợi để đảm bảo nó tồn tại
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Đang chờ tin nhắn. Nhấn Ctrl+C để thoát.");

        // 3. Định nghĩa callback để xử lý khi có tin nhắn đến
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Đã nhận: '" + message + "'");
        };

        // 4. Bắt đầu lắng nghe (queue, autoAck, callback, cancelCallback)
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}