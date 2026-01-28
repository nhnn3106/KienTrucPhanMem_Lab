package observer;

public class Main {
    public static void main(String[] args) {
        // --- Test Case 1: Cổ phiếu ---
        Stock apple = new Stock("AAPL", 150.0);
        Investor an = new Investor("An");
        Investor binh = new Investor("Bình");

        apple.attach(an);
        apple.attach(binh);

        apple.setPrice(155.5);

        // --- Test Case 2: Công việc ---
        ProjectTask task = new ProjectTask("Thiết kế Database");
        TeamMember dev = new TeamMember("Hoàng Dev");

        task.attach(dev);
        task.setStatus("In Progress");
    }
}