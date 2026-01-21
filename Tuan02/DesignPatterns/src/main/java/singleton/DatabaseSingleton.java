package singleton;

public class DatabaseSingleton {
    private static DatabaseSingleton instance;
    private static int connectCount = 0;

    private DatabaseSingleton() {
        connectCount++;
        System.out.println("--- Khởi tạo kết nối DUY NHẤT (Chỉ chạy 1 lần) ---");
    }

    public static DatabaseSingleton getInstance() {
        if (instance == null) {
            instance = new DatabaseSingleton();
        }
        return instance;
    }

    public static int getConnectCount() {
        return connectCount;
    }
}

