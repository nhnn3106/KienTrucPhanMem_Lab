package singleton;

public class DatabaseConnector {
    private static int connectionCount = 0;

    public DatabaseConnector() {
        connectionCount++;
        System.out.println("--- Đang thiết lập kết nối mới tới Database (Tốn tài nguyên) ---");
    }

    public static int getConnectionCount() {
        return connectionCount;
    }
}
