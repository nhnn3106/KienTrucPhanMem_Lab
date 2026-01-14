import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
    private final static String QUEUE_NAME = "java_queue";

    public static void main(String[] argv) throws Exception {
        // 1. Tạo ConnectionFactory và thiết lập thông số
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Docker đang chạy trên máy cục bộ

        // 2. Mở kết nối và tạo Channel
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 3. Khai báo hàng đợi (tên, durable, exclusive, autoDelete, arguments)
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = "Xin chào từ Java Producer!";

            // 4. Gửi tin nhắn
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));

            System.out.println(" [x] Đã gửi: '" + message + "'");
        }
    }
}
