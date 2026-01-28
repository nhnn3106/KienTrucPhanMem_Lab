package adapter;

public class XmlToJsonAdapter implements JsonService {
    private XmlSystem xmlSystem;

    public XmlToJsonAdapter(XmlSystem xmlSystem) {
        this.xmlSystem = xmlSystem;
    }

    @Override
    public void postData(String jsonContent) {
        System.out.println("Adapter: Đang chuyển đổi JSON sang XML...");

        // Giả lập logic chuyển đổi: {"name":"John"} -> <name>John</name>
        String convertedXml = convertJsonToXml(jsonContent);

        // Gửi dữ liệu đã chuyển đổi cho hệ thống XML
        xmlSystem.sendXml(convertedXml);
    }

    private String convertJsonToXml(String json) {
        // Trong thực tế, bạn sẽ dùng thư viện như Jackson hoặc Org.Json ở đây
        return json.replace("{", "<xml>")
                .replace("}", "</xml>")
                .replace(":", ">")
                .replace("\"", "");
    }
}