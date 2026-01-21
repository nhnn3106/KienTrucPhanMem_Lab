package singleton;

/**
 * Demo so sánh Singleton vs Không Singleton qua connectionCount
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║   DEMO: SO SÁNH SINGLETON VS KHÔNG SINGLETON (connectionCount)║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        demoWithoutSingleton();
        demoWithSingleton();
        compareResults();
    }

    /**
     * PHẦN 1: KHÔNG DÙNG SINGLETON
     * Mỗi lần new DatabaseConnector() sẽ tạo ra một đối tượng MỚI
     * → connectionCount tăng lên
     */
    private static void demoWithoutSingleton() {
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  PHẦN 1: KHÔNG DÙNG SINGLETON                               │");
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        // Module Hiển thị sản phẩm
        System.out.println("📱 Module Hiển thị sản phẩm:");
        DatabaseConnector conn1 = new DatabaseConnector();
        System.out.println("   → Tổng số kết nối: " + DatabaseConnector.getConnectionCount() + "\n");

        // Module Giỏ hàng
        System.out.println("🛒 Module Giỏ hàng:");
        DatabaseConnector conn2 = new DatabaseConnector();
        System.out.println("   → Tổng số kết nối: " + DatabaseConnector.getConnectionCount() + "\n");

        // Module Thanh toán
        System.out.println("💳 Module Thanh toán:");
        DatabaseConnector conn3 = new DatabaseConnector();
        System.out.println("   → Tổng số kết nối: " + DatabaseConnector.getConnectionCount() + "\n");

        // Kiểm tra: Các đối tượng có giống nhau không?
        System.out.println("🔍 KIỂM TRA:");
        System.out.println("   conn1 == conn2: " + (conn1 == conn2) + " (Khác đối tượng!)");
        System.out.println("   conn2 == conn3: " + (conn2 == conn3) + " (Khác đối tượng!)");
        System.out.println("   conn1 == conn3: " + (conn1 == conn3) + " (Khác đối tượng!)\n");

        // Kết luận
        System.out.println("❌ HẬU QUẢ:");
        System.out.println("   • Đã tạo " + DatabaseConnector.getConnectionCount() + " kết nối khác nhau");
        System.out.println("   • Mỗi module có một kết nối riêng → Lãng phí tài nguyên");
        System.out.println("   • Nếu có 1000 users → 3000 kết nối → Server CRASH! 💥\n");
    }

    /**
     * PHẦN 2: CÓ DÙNG SINGLETON
     * Dù gọi getInstance() bao nhiêu lần, chỉ TẠO MỘT LẦN duy nhất
     * → connectCount = 1 (chỉ tăng 1 lần duy nhất)
     */
    private static void demoWithSingleton() {
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  PHẦN 2: CÓ DÙNG SINGLETON                                  │");
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        // Module Hiển thị sản phẩm
        System.out.println("📱 Module Hiển thị sản phẩm:");
        DatabaseSingleton conn1 = DatabaseSingleton.getInstance();
        System.out.println("   → Số lần khởi tạo kết nối: " + DatabaseSingleton.getConnectCount() + "\n");

        // Module Giỏ hàng
        System.out.println("🛒 Module Giỏ hàng:");
        DatabaseSingleton conn2 = DatabaseSingleton.getInstance();
        System.out.println("   → Số lần khởi tạo kết nối: " + DatabaseSingleton.getConnectCount() + " (Không tạo thêm!)");


        // Module Thanh toán
        System.out.println("💳 Module Thanh toán:");
        DatabaseSingleton conn3 = DatabaseSingleton.getInstance();
        System.out.println("   → Số lần khởi tạo kết nối: " + DatabaseSingleton.getConnectCount() + " (Không tạo thêm!)");


        // Kiểm tra: Các đối tượng có giống nhau không?
        System.out.println("🔍 KIỂM TRA:");
        System.out.println("   conn1 == conn2: " + (conn1 == conn2) + " (Cùng đối tượng!)");
        System.out.println("   conn2 == conn3: " + (conn2 == conn3) + " (Cùng đối tượng!)");
        System.out.println("   conn1 == conn3: " + (conn1 == conn3) + " (Cùng đối tượng!)\n");

        // Kết luận
        System.out.println("✅ LỢI ÍCH:");
        System.out.println("   • Chỉ tạo " + DatabaseSingleton.getConnectCount() + " kết nối duy nhất");
        System.out.println("   • Tất cả module dùng CHUNG một kết nối → Tiết kiệm tài nguyên");
        System.out.println("   • Nếu có 1000 users → VẪN CHỈ 1 kết nối → Server ổn định! ✅\n");
    }

    /**
     * PHẦN 3: SO SÁNH KẾT QUẢ
     * Hiển thị bảng so sánh connectionCount giữa hai cách tiếp cận
     */
    private static void compareResults() {
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│  PHẦN 3: SO SÁNH KẾT QUẢ                                   │");
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        System.out.println("📊 BẢNG SO SÁNH:");
        System.out.println("┌────────────────────────┬──────────────┬──────────────┐");
        System.out.println("│ Tiêu chí              │ Không Sing.  │ Có Singleton │");
        System.out.println("├────────────────────────┼──────────────┼──────────────┤");
        System.out.println("│ Số lần tạo kết nối    │      " + DatabaseConnector.getConnectionCount() + "       │       " + DatabaseSingleton.getConnectCount() + "      │");
        System.out.println("│ Số đối tượng khác nhau│      " + DatabaseConnector.getConnectionCount() + "       │       " + DatabaseSingleton.getConnectCount() + "      │");
        System.out.println("│ Bộ nhớ (giả sử 50MB)  │    150 MB    │     50 MB    │");
        System.out.println("│ Tái sử dụng           │     Không    │      Có      │");
        System.out.println("│ Đồng bộ dữ liệu       │      Khó     │      Dễ      │");
        System.out.println("└────────────────────────┴──────────────┴──────────────┘\n");

        System.out.println("💡 KẾT LUẬN QUAN TRỌNG:");
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│ • KHÔNG SINGLETON: connectionCount = " + DatabaseConnector.getConnectionCount() + "                          │");
        System.out.println("│   → Mỗi lần new tạo đối tượng MỚI                          │");
        System.out.println("│   → Lãng phí tài nguyên, khó quản lý                       │");
        System.out.println("│                                                             │");
        System.out.println("│ • CÓ SINGLETON: connectCount = " + DatabaseSingleton.getConnectCount() + "                               │");
        System.out.println("│   → Chỉ tạo một lần duy nhất trong constructor             │");
        System.out.println("│   → Các lần sau chỉ trả về instance có sẵn                 │");
        System.out.println("│   → Tiết kiệm tài nguyên, dễ quản lý                       │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
    }
}

