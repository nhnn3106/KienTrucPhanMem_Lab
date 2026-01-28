package adapter;

public class Main {
    public static void main(String[] args) {
        // 1. Khởi tạo hệ thống XML cũ
        XmlSystem legacySystem = new XmlSystem();

        // 2. Tạo bộ chuyển đổi để hệ thống cũ có thể "hiểu" được lệnh JSON
        JsonService webService = new XmlToJsonAdapter(legacySystem);

        // 3. Client gửi dữ liệu định dạng JSON
        String myData = "{\"message\":\"Hello World\"}";

        System.out.println("Client: Gửi dữ liệu JSON...");
        webService.postData(myData);
    }
}
